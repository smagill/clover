package net.kemitix.clover.spi;

import net.kemitix.properties.typed.TypedProperties;

import java.awt.Graphics2D;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Image {
    Image crop(Region region);

    default Image apply(final Function<Image, Image> function) {
        return function.apply(this);
    }

    void write(Path path, String name, TypedProperties properties);

    Image rescale(float scale);

    Image withFilledArea(Region region, String fillColour);

    Area getArea();

    Region getRegion();

    Image withGraphics(Consumer<Graphics2D> graphics2DEffect);

    String getNameQualifier();
}
