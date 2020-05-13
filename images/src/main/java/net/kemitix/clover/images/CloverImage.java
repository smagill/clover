package net.kemitix.clover.images;

import lombok.Builder;
import lombok.Getter;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.FatalCloverError;
import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.*;
import net.kemitix.properties.typed.TypedProperties;
import org.beryx.awt.color.ColorFactory;

import javax.enterprise.inject.Instance;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static java.awt.Image.SCALE_SMOOTH;

@Builder(toBuilder = true)
class CloverImage implements Image {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImage.class.getName());

    private final BufferedImage image;
    private final CloverProperties config;
    private final FontCache fontCache;
    private final Instance<ImageWriter> imageWriters;
    @Getter
    private final String nameQualifier;

    CloverImage(
            final BufferedImage image,
            final CloverProperties config,
            final FontCache fontCache,
            final Instance<ImageWriter> imageWriters,
            final String nameQualifier
    ) {
        this.image = image;
        this.config = config;
        this.fontCache = fontCache;
        this.imageWriters = imageWriters;
        this.nameQualifier = nameQualifier;
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

    static Color getColor(final String colour) {
        return Optional.ofNullable(
                ColorFactory.valueOf(colour))
                .orElseThrow(() ->
                        new FatalCloverError(
                                "Unknown colour: " + colour));
    }

    @Override
    public Area getArea() {
        return Area.builder()
                .width(image.getWidth())
                .height(image.getHeight()).build();
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
        return new CloverImage(newImage, config, fontCache, imageWriters, "");
    }

}
