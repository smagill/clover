package net.kemitix.clover.spi.images;

import net.kemitix.properties.typed.TypedProperties;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public interface Image {
    Image scaleToCover(Area area);

    Image crop(Region region);

    default Image apply(final Function<Image, Image> function) {
        return function.apply(this);
    }

    void write(Path path, String name, TypedProperties properties);

    Image withText(
            String title,
            XY topLeft,
            FontFace fontFace);

    Image withText(
            List<String> title,
            XY topLeft,
            FontFace fontFace);

    Image rescale(float scale);

    Image withFilledArea(Region region, String fillColour);

    Image withRotatedCenteredText(String text, Region region, FontFace fontFace);

    Area getArea();

    Region getRegion();
}
