package net.kemitix.cossmass.clover.images.imglib;

import ij.ImagePlus;
import net.kemitix.cossmass.clover.images.Image;

import java.util.logging.Logger;

public class ImgLibImage implements Image {


    private static final Logger LOGGER =
            Logger.getLogger(
                    ImgLibImage.class.getName());
    private final ImagePlus imagePlus;

    public ImgLibImage(final ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }

    @Override
    public Image scaleToCover(
            final int height,
            final int width
    ) {
        LOGGER.info(String.format("Scaling to cover: %d x %d", height, width));
        //TODO scale to cover
        return this;
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
        //TODO crop
        return this;
    }

}
