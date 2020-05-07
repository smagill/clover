package net.kemitix.clover.service;

import net.kemitix.clover.spi.images.Image;
import net.kemitix.properties.typed.TypedProperties;

public interface CloverFormat {
    Image getImage();

    String getName();

    TypedProperties getImageProperties();
}
