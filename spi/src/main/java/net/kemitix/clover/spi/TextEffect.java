package net.kemitix.clover.spi;

import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.Region;

import java.util.function.Function;

public interface TextEffect {

    RegionNext fontFace(FontFace fontFace);

    interface RegionNext {
        TextNext region(Region region);
    }

    interface TextNext {
        Function<Image, Image> text(String text);
    }

}
