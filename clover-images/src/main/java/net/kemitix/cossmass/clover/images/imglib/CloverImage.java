package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import static java.awt.Image.SCALE_SMOOTH;

class CloverImage implements Image {


    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImage.class.getName());

    private final BufferedImage image;
    private final CloverConfig config;

    CloverImage(
            final BufferedImage image,
            final CloverConfig config
    ) {
        this.image = image;
        this.config = config;
    }

    @Override
    public Image scaleToCover(
            final int width,
            final int height
    ) {
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
        return scaleTo(newWidth, newHeight);
    }

    private Image scaleTo(final int width, final int height) {
        final BufferedImage resized =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(image.getScaledInstance(width, height, SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return new CloverImage(resized, config);
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public Image crop(
            final int xOffset,
            final int yOffset,
            final int width,
            final int height
    ) {
        LOGGER.info(String.format("Cropping from %d x %d by %d x %d",
                xOffset, yOffset,
                width, height
        ));
        final BufferedImage cropped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        cropped.createGraphics()
                .drawImage(
                        image.getSubimage(xOffset, yOffset, width, height),
                        0,
                        0,
                        null);
        return new CloverImage(cropped, config);
    }

    @Override
    public void write(
            final Path path,
            final String name
    ) {
        LOGGER.info(String.format("Writing %s to %s", name, path));
        config.getImageTypes()
                .forEach(format -> {
                    final File file = path.resolve(name + "." + format).toFile();
                    write(format, file);
                });
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
