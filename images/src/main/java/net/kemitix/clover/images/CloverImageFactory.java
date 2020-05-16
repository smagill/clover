package net.kemitix.clover.images;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.Image;
import net.kemitix.clover.spi.ImageFactory;
import net.kemitix.clover.spi.ImageWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.awt.image.BufferedImage;

@ApplicationScoped
public class CloverImageFactory implements ImageFactory {

    @Inject
    CloverProperties config;
    @Inject
    FontCache fontCache;
    @Inject
    Instance<ImageWriter> imageWriters;

    @Override
    public Image create(
            BufferedImage image,
            String name
    ) {
        return new CloverImage(image, config, fontCache, imageWriters, name);
    }
}
