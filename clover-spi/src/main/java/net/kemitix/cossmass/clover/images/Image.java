package net.kemitix.cossmass.clover.images;

import java.util.function.Function;

public interface Image {
    Image scaleToCover(int height, int width);

    Image crop(int xOffset, int yOffset, int width, int height);

    default Image apply(final Function<Image, Image> function) {
        return function.apply(this);
    }
}
