package net.kemitix.clover.spi.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageFactory {

    Image create(BufferedImage image, String name);
}
