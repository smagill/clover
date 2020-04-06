package net.kemitix.clover.images;

import net.kemitix.properties.typed.TypedProperties;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageWriter {
    boolean accepts(String format);

    void write(
            BufferedImage image,
            File file,
            TypedProperties properties);
}
