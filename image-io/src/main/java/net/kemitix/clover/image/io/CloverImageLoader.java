package net.kemitix.clover.image.io;

import net.kemitix.clover.spi.FatalCloverError;
import net.kemitix.clover.spi.Image;
import net.kemitix.clover.spi.ImageLoader;
import net.kemitix.clover.spi.ImageFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class CloverImageLoader implements ImageLoader {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImageLoader.class.getName());

    @Inject
    ImageFactory imageFactory;

    @Override
    public Image load(final File file) throws IOException {
        LOGGER.info("Loading " + file.getCanonicalPath());
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IIOException e) {
            throw new FatalCloverError("Error loading file: " + file, e);
        }
        LOGGER.info(String.format("Loaded: (%dx%d)",
                image.getWidth(),
                image.getHeight()));
        return imageFactory.create(image, "");
    }
}
