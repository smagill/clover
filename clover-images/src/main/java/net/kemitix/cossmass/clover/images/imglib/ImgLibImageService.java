package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;

import java.io.File;
import java.util.logging.Logger;

public class ImgLibImageService implements ImageService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ImgLibImageService.class.getName());

    @Override
    public Image load(final File file) {
        LOGGER.info("Loading " + file);
        return new ImgLibImage();
    }
}
