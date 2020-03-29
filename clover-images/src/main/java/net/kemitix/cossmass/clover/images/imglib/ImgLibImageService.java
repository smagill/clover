package net.kemitix.cossmass.clover.images.imglib;

import ij.ImagePlus;
import ij.io.Opener;
import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;

import java.io.File;
import java.util.logging.Logger;

public class ImgLibImageService implements ImageService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ImgLibImageService.class.getName());

    private final Opener opener = new Opener();

    @Override
    public Image load(final File file) {
        LOGGER.info("Loading " + file);
        final ImagePlus imp = opener.openImage(file.getAbsolutePath());
        return new ImgLibImage(imp);
    }
}
