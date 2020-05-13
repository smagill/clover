package net.kemitix.clover.service;

import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.Region;
import net.kemitix.properties.typed.TypedProperties;

import java.util.List;
import java.util.function.Function;

public interface CloverFormat {
    List<Image> getImages();

    String getName();

    TypedProperties getImageProperties();

    default Function<Image, Image> crop(Region cropRegion) {
        return image -> image.crop(cropRegion);
    }

    default Function<Image, Image> rescale(float factor) {
        return image -> image.rescale(factor);
    }
}
