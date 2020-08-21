package net.kemitix.clover.service;

import lombok.extern.java.Log;
import net.kemitix.clover.spi.Image;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Log
@ApplicationScoped
public class Paperback implements CloverFormat {

    @Inject IssueDimensions dimensions;
    @Inject Image coverArtImage;
    @Inject Instance<Block<Graphics2D>> blocks;
    @Inject CloverProperties cloverProperties;

    private List<Image> images;

    @Override
    public List<Image> getImages() {
        if (images == null) {
            log.info("Generating Paperback images...");
            init();
            log.info("Generated Paperback images");
        }
        return images;
    }

    public void init() {
        images = Collections.singletonList(
                rescale(dimensions.getScaleFromOriginal())
                        .andThen(crop(dimensions.getWrapCrop()))
                        .andThen(blocks())
                        .apply(coverArtImage));
    }

    private Function<Image, Image> blocks() {
        var properties = TypedProperties.create();
        return image ->
                image.withGraphics(graphics2D ->
                        Drawable.draw(blocks, graphics2D, properties));
    }

    @Override
    public String getName() {
        return "paperback";
    }

    @Override
    public TypedProperties getImageProperties() {
        float width = dimensions.getPaperbackCoverWithTrim().getWidth();
        float height = dimensions.getPaperbackCoverWithTrim().getHeight();
        return TypedProperties.create()
                .with(PdfWidth.class, width)
                .with(PdfHeight.class, height);
    }

    @Override
    public boolean isEnabled() {
        return cloverProperties.isEnablePaperback();
    }

}
