package net.kemitix.clover.spi;

import java.awt.image.BufferedImage;

public interface ImageFactory {

    Image create(BufferedImage image, String name);
}
