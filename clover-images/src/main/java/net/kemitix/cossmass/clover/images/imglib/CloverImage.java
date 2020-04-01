package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.Area;
import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.*;
import org.beryx.awt.color.ColorFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.awt.Image.SCALE_SMOOTH;

class CloverImage implements Image {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImage.class.getName());

    private final BufferedImage image;
    private final CloverConfig config;
    private final FontCache fontCache;

    CloverImage(
            final BufferedImage image,
            final CloverConfig config,
            final FontCache fontCache
    ) {
        this.image = image;
        this.config = config;
        this.fontCache = fontCache;
    }

    @Override
    public Image scaleToCover(final Area area) {
        final int width = area.getWidth();
        final int height = area.getHeight();
        LOGGER.info(String.format("Scaling to cover: %d x %d", width, height));
        final int originalWidth = getWidth();
        final int originalHeight = getHeight();
        final int ratio = originalWidth / originalHeight;
        LOGGER.info("Ratio: " + ratio);
        final int newWidth;
        final int newHeight;
        if (ratio > 1) { // is wide
            newWidth = height * ratio;
            newHeight = height;
        } else { // is tall
            newWidth = width;
            newHeight = width / ratio;
        }
        LOGGER.info(String.format("Resizing to %dx%d",
                newWidth, newHeight));
        return scaleTo(Area.of(newWidth, newHeight));
    }

    private Image scaleTo(final Area area) {
        final int width = area.getWidth();
        final int height = area.getHeight();
        final BufferedImage resized =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        resized.createGraphics()
                .drawImage(
                        image.getScaledInstance(width, height, SCALE_SMOOTH),
                        0,
                        0,
                        null);
        return new CloverImage(resized, config, fontCache);
    }

    private int getHeight() {
        return image.getHeight();
    }

    private int getWidth() {
        return image.getWidth();
    }

    @Override
    public Image crop(
            final XY cropOffset,
            final Area area
    ) {
        final int xOffset = cropOffset.getX();
        final int yOffset = cropOffset.getY();
        final int width = area.getWidth();
        final int height = area.getHeight();
        LOGGER.info(String.format("Cropping from %d x %d by %d x %d",
                xOffset, yOffset,
                width, height
        ));
        final BufferedImage cropped =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        cropped.createGraphics()
                .drawImage(
                        image.getSubimage(xOffset, yOffset, width, height),
                        0,
                        0,
                        null);
        return new CloverImage(cropped, config, fontCache);
    }

    @Override
    public void write(
            final Path path,
            final String name
    ) {
        LOGGER.info(String.format("Writing %s to %s", name, path));
        config.getImageTypes()
                .forEach(format -> {
                    final File file =
                            path.resolve(name + "." + format)
                                    .toFile();
                    write(format, file);
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
        final BufferedImage withText = copyImage();
        final Graphics2D graphics = withText.createGraphics();
        drawText(text, framing -> topLeft, fontFace, graphics);
        return new CloverImage(withText, config, fontCache);
    }

    private void drawText(
            final String text,
            final Function<Framing, XY> positioning,
            final FontFace fontFace,
            final Graphics2D graphics
    ) {
        final Font font = fontCache.loadFont(fontFace);
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
                .map(head ->
                        withText(head, topLeft, fontFace)
                                .withText(
                                        tail(text),
                                        XY.at(
                                                topLeft.getX(),
                                                topLeft.getY() + lineHeight(head, fontFace)),
                                        fontFace))
                .orElse(this);
    }

    @Override
    public Image rescale(final float scale) {
        return scaleTo(Area.of(
                ((int) (getWidth() * scale)),
                ((int) (getHeight() * scale))));
    }

    @Override
    public Image withFilledArea(
            final XY topLeft,
            final Area area,
            final String fillColour
    ) {
        LOGGER.fine(String.format("Filled Area: %dx%d by %dx%d",
                topLeft.getX(), topLeft.getY(),
                area.getWidth(), area.getHeight()));
        final BufferedImage withFilledArea = copyImage();
        final Graphics2D graphics = withFilledArea.createGraphics();
        graphics.setPaint(getColor(fillColour));
        graphics.fillRect(topLeft.getX(), topLeft.getY(), area.getWidth(), area.getHeight());
        return new CloverImage(withFilledArea, config, fontCache);
    }

    @Override
    public Image withRotatedCenteredText(
            final String text,
            final XY topLeft,
            final Area area,
            final FontFace fontFace
    ) {
        LOGGER.info(String.format("Drawing text: %s - %d",
                text, fontFace.getSize()));
        final BufferedImage withText = copyImage();
        final Graphics2D graphics = withText.createGraphics();
        graphics.rotate(Math.PI / 2);
        drawText(text,
                framing -> framing
                        .toBuilder()
                        .outer(Area.builder()
                                .width(area.getHeight())
                                .height(area.getWidth())
                                .build())
                        .build()
                        .centered()
                        .map(xy -> XY.at(xy.getX() + topLeft.getY(), framing.getInner().getHeight() + topLeft.getX() + xy.getY()))
                        .map(xy -> XY.at(xy.getX(), -xy.getY())),
                fontFace, graphics);
        return new CloverImage(withText, config, fontCache);
    }

    private int lineHeight(
            final String head,
            final FontFace fontFace
    ) {
        final Graphics2D graphics = image.createGraphics();
        final FontMetrics fontMetrics = graphics.getFontMetrics(fontCache.loadFont(fontFace));
        final LineMetrics lineMetrics = fontMetrics.getLineMetrics(head, graphics);
        final float height = lineMetrics.getHeight();
        return (int) height;
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

    private Color getColor(final String colour) {
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
            final File file
    ) {
        LOGGER.info(String.format("Writing %s file as %s", format, file));
        try {
            if (ImageIO.write(image, format, file)) {
                LOGGER.info("Wrote: " + file);
            } else {
                LOGGER.severe("No writer found for " + format);
            }
        } catch (final IOException e) {
            LOGGER.severe("Failed to write " + file);
        }
    }

}
