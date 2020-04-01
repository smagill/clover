package net.kemitix.cossmass.clover.images;

import java.nio.file.Path;
import java.util.function.Function;

public interface Image {
    Image scaleToCover(int width, int height);

    Image crop(int xOffset, int yOffset, int width, int height);

    default Image apply(final Function<Image, Image> function) {
        return function.apply(this);
    }

    void write(Path path, String name);

    Image withText(
            String title,
            XY xy,
            FontFace fontFace);
}
