package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class CloverImageService implements ImageService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImageService.class.getName());
    private final CloverConfig config;

    public CloverImageService(final CloverConfig config) {
        this.config = config;
    }

    @Override
    public Image load(final File file) throws IOException {
        LOGGER.info("Loading " + file);
        final BufferedImage image = ImageIO.read(file);
        return new CloverImage(image, config);
    }
}
