package net.kemitix.clover.images;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.FatalCloverError;
import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Dependent
public class CloverImageService implements ImageService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImageService.class.getName());
    private final CloverProperties config;
    private final FontCache fontCache;
    private final Instance<ImageWriter> imageWriters;

    @Inject
    public CloverImageService(
            final CloverProperties config,
            final FontCache fontCache,
            final Instance<ImageWriter> imageWriters
    ) {
        this.config = config;
        this.fontCache = fontCache;
        this.imageWriters = imageWriters;
    }

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
        return new CloverImage(image, config, fontCache, imageWriters);
    }
}
