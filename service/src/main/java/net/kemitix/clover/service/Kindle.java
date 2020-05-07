package net.kemitix.clover.service;

import net.kemitix.clover.spi.images.Image;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Kindle implements CloverFormat {

    private Dimensions dimensions;
    private Paperback paperback;

    public Kindle() {
    }

    @Inject
    protected Kindle(
            Dimensions dimensions,
            final Paperback paperback
    ) {
        this.dimensions = dimensions;
        this.paperback = paperback;
    }

    @Override
    public Image getImage() {
        return paperback.getImage()
                .crop(dimensions.getFrontCrop());
    }

    @Override
    public String getName() {
        return "kindle";
    }

    @Override
    public TypedProperties getImageProperties() {
        return TypedProperties.create();
    }
}
