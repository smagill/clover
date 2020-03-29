package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.Image;

import java.util.logging.Logger;

public class ImgLibImage implements Image {


    private static final Logger LOGGER =
            Logger.getLogger(
                    ImgLibImage.class.getName());

    @Override
    public Image scaleToCover(
            final int height,
            final int width
    ) {
        LOGGER.info(String.format("Scaling to cover: %d x, %d", height, width));
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
        return this;
    }

}
