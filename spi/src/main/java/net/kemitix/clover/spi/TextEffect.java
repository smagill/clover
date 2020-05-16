package net.kemitix.clover.spi;

import java.util.function.Function;

public interface TextEffect<T> {

    RegionNext<T> fontFace(FontFace fontFace);

    interface RegionNext<T> {
        TextNext<T> region(Region region);
    }

    interface TextNext<T> {
        Function<T, T> text(String text);
    }

}
