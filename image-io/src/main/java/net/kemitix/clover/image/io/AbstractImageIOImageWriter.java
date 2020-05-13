package net.kemitix.clover.image.io;

import net.kemitix.clover.spi.images.ImageWriter;
import net.kemitix.properties.typed.TypedProperties;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

abstract class AbstractImageIOImageWriter
        implements ImageWriter {

    private static final Logger LOGGER =
            Logger.getLogger(
                    AbstractImageIOImageWriter.class.getName());

    @Override
    public void write(
            final BufferedImage image,
            final File file,
            final TypedProperties properties
    ) {
        try {
            ImageIO.write(image, getFormatName(), file);
            LOGGER.info(String.format("Wrote %s", file));
        } catch (final IOException e) {
            LOGGER.severe("Failed to write " + file);
        }

    }

    protected abstract String getFormatName();
}
