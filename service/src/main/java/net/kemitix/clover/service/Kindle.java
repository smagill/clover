package net.kemitix.clover.service;

import net.kemitix.clover.spi.images.Image;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Image> getImages() {
        return paperback.getImages().stream()
                .map(image -> image.crop(dimensions.getFrontCrop()))
                .collect(Collectors.toList());
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
