package net.kemitix.clover.service;

import lombok.Getter;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.PdfHeight;
import net.kemitix.clover.spi.PdfWidth;
import net.kemitix.clover.spi.images.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;
import java.util.logging.Logger;

@ApplicationScoped
public class Paperback implements CloverFormat {

    private Dimensions dimensions;
    private Image coverArtImage;
    private FrontCover frontCover;
    private Spine spine;
    private BackCover backCover;
    @Getter
    private Image image;

    public Paperback() {
    }

    @Inject
    protected Paperback(
            final Dimensions dimensions,
            final Image coverArtImage,
            final FrontCover frontCover,
            final Spine spine,
            final BackCover backCover
    ) {
        this.dimensions = dimensions;
        this.coverArtImage = coverArtImage;
        this.frontCover = frontCover;
        this.spine = spine;
        this.backCover = backCover;
    }

    @PostConstruct
    public void init() {
        image = rescale(dimensions.getScaleFromOriginal())
                .andThen(crop(dimensions.getWrapCrop()))
                .andThen(frontCover)
                .andThen(spine)
                .andThen(backCover)
                .apply(coverArtImage);
    }

    private Function<Image, Image> crop(Region cropRegion) {
        return image -> image.crop(cropRegion);
    }

    private Function<Image, Image> rescale(float factor) {
        return image -> image.rescale(factor);
    }

    @Override
    public String getName() {
        return "paperback";
    }

    @Override
    public TypedProperties getImageProperties() {
        Region wrapCrop = dimensions.getWrapCrop();
        return TypedProperties.create()
                .with(PdfWidth.class, (int) wrapCrop.getWidth())
                .with(PdfHeight.class, (int) wrapCrop.getHeight());
    }

}
