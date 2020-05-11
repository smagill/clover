package net.kemitix.clover.images;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.FatalCloverError;
import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.*;
import net.kemitix.properties.typed.TypedProperties;
import org.beryx.awt.color.ColorFactory;

import javax.enterprise.inject.Instance;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.awt.Image.SCALE_SMOOTH;

class CloverImage implements Image {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImage.class.getName());

    private final BufferedImage image;
    private final CloverProperties config;
    private final FontCache fontCache;
    private final Instance<ImageWriter> imageWriters;

    CloverImage(
            final BufferedImage image,
            final CloverProperties config,
            final FontCache fontCache,
            final Instance<ImageWriter> imageWriters
    ) {
        this.image = image;
        this.config = config;
        this.fontCache = fontCache;
        this.imageWriters = imageWriters;
    }

    @Override
    public Image scaleToCover(final Area area) {
        final float width = area.getWidth();
        final float height = area.getHeight();
        LOGGER.info(String.format("Scaling to cover: %f x %f", width, height));
        final float originalWidth = getWidth();
        final float originalHeight = getHeight();
        final float ratio = originalWidth / originalHeight;
        LOGGER.info("Ratio: " + ratio);
        final float newWidth;
        final float newHeight;
        if (ratio > 1) { // is wide
            newWidth = height * ratio;
            newHeight = height;
        } else { // is tall
            newWidth = width;
            newHeight = width / ratio;
        }
        LOGGER.info(String.format("Resizing to %fx%f",
                newWidth, newHeight));
        return scaleTo(Area.of(newWidth, newHeight));
    }

    private Image scaleTo(final Area area) {
        final int width = (int) area.getWidth();
        final int height = (int) area.getHeight();
        final BufferedImage resized =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return useGraphics(resized, graphics2D ->
                graphics2D.drawImage(
                        image.getScaledInstance(width, height, SCALE_SMOOTH),
                        0,
                        0,
                        null));
    }

    private int getHeight() {
        return image.getHeight();
    }

    private int getWidth() {
        return image.getWidth();
    }

    @Override
    public Image crop(final Region region) {
        LOGGER.info(String.format("Cropping %s", getRegion()));
        LOGGER.info(String.format("      to %s", region));
        getRegion().mustContain(region);
        final BufferedImage cropped =
                new BufferedImage(
                        region.getWidth(), region.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
        return useGraphics(cropped, graphics2D ->
                graphics2D.drawImage(
                        image.getSubimage(
                                region.getLeft(),
                                region.getTop(),
                                region.getWidth(),
                                region.getHeight()),
                        0, 0, null));
    }

    @Override
    public Region getRegion() {
        return Region.builder()
                .width(image.getWidth())
                .height(image.getHeight())
                .build();
    }

    @Override
    public Image withGraphics(Consumer<Graphics2D> graphics2DEffect) {
        return useGraphics(copyImage(), graphics2DEffect);
    }

    @Override
    public BufferedImage getBufferedImage() {
        return image;
    }

    @Override
    public void write(
            final Path path,
            final String name,
            final TypedProperties properties
    ) {
        LOGGER.info(String.format("Writing %s to %s", name, path));
        config.getImageTypes()
                .forEach(format -> {
                    final File file =
                            path.resolve(name + "." + format)
                                    .toFile();
                    write(format, file, properties);
                });
    }

    @Override
    public Image withText(
            final String text,
            final XY topLeft,
            final FontFace fontFace
    ) {
        if ("".equals(text)) {
            return this;
        }
        LOGGER.info(String.format("Drawing text: %s at %dx%d - %d",
                text,
                topLeft.getX(),
                topLeft.getY(),
                fontFace.getSize()));
        return withGraphics(graphics2D ->
                drawText(text, framing -> topLeft, fontFace, graphics2D, fontCache, image));
    }

    public static void drawText(
            final String text,
            final Function<Framing, XY> positioning,
            final FontFace fontFace,
            final Graphics2D graphics,
            final FontCache fontCache,
            final BufferedImage image
    ) {
        final Font font = fontCache.loadFont(fontFace);
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        graphics.setFont(font);
        final Rectangle2D stringBounds =
                font.getStringBounds(text, graphics.getFontRenderContext());
        final XY topLeft = positioning.apply(Framing.builder()
                .outer(Area.of(image.getWidth(), image.getHeight()))
                .inner(Area.of(((int) stringBounds.getWidth()), ((int) stringBounds.getHeight())))
                .build());
        // Drop Shadow
        final XY shadowOffset = fontFace.getShadowOffset();
        if (shadowOffset.getX() != 0 || shadowOffset.getY() != 0) {
            graphics.setPaint(getColor(fontFace.getShadowColour()));
            graphics.drawString(text,
                    topLeft.getX() + shadowOffset.getX(),
                    (int) (topLeft.getY() - stringBounds.getY() + shadowOffset.getY()));
        }
        // Text
        graphics.setPaint(getColor(fontFace.getColour()));
        graphics.drawString(text,
                topLeft.getX(),
                (int) (topLeft.getY() - stringBounds.getY()));
//        if (config.drawBoundingBoxes) {
//            graphics.setPaint(getColor("red"));
//            graphics.drawRect(topLeft.getX(), topLeft.getY(), ((int) stringBounds.getWidth()), ((int) stringBounds.getHeight()));
//        }
    }

    @Override
    public Image withText(
            final List<String> text,
            final XY topLeft,
            final FontFace fontFace
    ) {
        return head(text)
                .map(head -> withText(head, topLeft, fontFace)
                        .withText(tail(text),
                                XY.at(topLeft.getX(), topLeft.getY() +
                                        ((int) textArea(head, fontFace)
                                                .getHeight())),
                                fontFace))
                .orElse(this);
    }

    @Override
    public Image rescale(final float scale) {
        LOGGER.info("Rescale from: " + getRegion());
        LOGGER.info(" by: " + scale);
        Area area = Area.of(
                (int) (getWidth() * scale),
                (int) (getHeight() * scale));
        LOGGER.info(" to: " + area);
        return scaleTo(area);
    }

    @Override
    public Image withFilledArea(
            final Region region,
            final String fillColour
    ) {
        final int top = region.getTop();
        final int left = region.getLeft();
        final int width = region.getWidth();
        final int height = region.getHeight();
        LOGGER.fine(String.format("Filled Area: %dx%d by %dx%d",
                left, top, width, height));
        return withGraphics(graphics2D -> {
            graphics2D.setPaint(getColor(fillColour));
            graphics2D.fillRect(left, top, width, height);
        });
    }

    @Override
    public Image withRotatedCenteredText(
            final String text,
            final Region region,
            final FontFace fontFace
    ) {
        LOGGER.info(String.format("Drawing text: %s - %d",
                text, fontFace.getSize()));
        return withGraphics(graphics2D -> {
            graphics2D.rotate(Math.PI / 2);
            CloverImage.drawText(text,
                    framing -> framing
                            .toBuilder()
                            .outer(Area.builder()
                                    .width(region.getHeight())
                                    .height(region.getWidth())
                                    .build())
                            .build()
                            .centered()
                            .map(xy -> XY.at(
                                    xy.getX() + region.getTop(),
                                    (int) (framing.getInner().getHeight() + region.getLeft() + xy.getY())))
                            .map(xy -> XY.at(xy.getX(), -xy.getY())),
                    fontFace, graphics2D, fontCache, image);
        });
    }

    @Override
    public Area getArea() {
        return Area.builder()
                .width(image.getWidth())
                .height(image.getHeight()).build();
    }

    @Override
    public Area textArea(
            final String text,
            final FontFace fontFace
    ) {
        final Graphics2D graphics = image.createGraphics();
        final FontMetrics fontMetrics =
                graphics.getFontMetrics(fontCache.loadFont(fontFace));
        return Area.of(
                fontMetrics.stringWidth(text),
                fontMetrics.getHeight());
    }

    private List<String> tail(final List<String> list) {
        if (list.size() < 1) {
            return Collections.emptyList();
        }
        return list.subList(1, list.size());
    }

    private Optional<String> head(final List<String> list) {
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(0));
    }

    private static Color getColor(final String colour) {
        return Optional.ofNullable(
                ColorFactory.valueOf(colour))
                .orElseThrow(() ->
                        new FatalCloverError(
                                "Unknown colour: " + colour));
    }

    private BufferedImage copyImage() {
        final BufferedImage copy =
                new BufferedImage(getWidth(), getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
        copy.createGraphics()
                .drawImage(image, 0, 0, null);
        return copy;
    }

    private void write(
            final String format,
            final File file,
            final TypedProperties properties
    ) {
        LOGGER.info(String.format("Writing %s file as %s", format, file));
        imageWriters.stream()
                .filter(iw -> iw.accepts(format))
                .findFirst()
                .ifPresentOrElse(
                        writer -> writer.write(image, file, properties),
                        () -> LOGGER.warning(String.format(
                                "No ImageWriter found for %s",
                                format)));
    }

    public Image useGraphics(
            BufferedImage bufferedImage,
            Consumer<Graphics2D> graphics2DEffect
    ) {
        graphics2DEffect.accept(bufferedImage.createGraphics());
        return withBufferedImage(bufferedImage);
    }

    private Image withBufferedImage(BufferedImage newImage) {
        return new CloverImage(newImage, config, fontCache, imageWriters);
    }

}
