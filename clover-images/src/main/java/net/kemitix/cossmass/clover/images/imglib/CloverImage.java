package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

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
            final int height,
            final int width
    ) {
        LOGGER.info(String.format("Scaling to cover: %d x %d", height, width));
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
        //TODO final BufferedImage resized = image.resize(newWidth, newHeight);
        return new CloverImage(image);
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
        //TODO final BufferedImage cropped = image.crop(xOffset, yOffset, width, height);
        return new CloverImage(image, config);
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
            ImageIO.write(image, format, file);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
