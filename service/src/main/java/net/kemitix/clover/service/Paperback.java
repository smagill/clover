package net.kemitix.clover.service;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.clover.spi.Image;
import net.kemitix.properties.typed.TypedProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

@ApplicationScoped
public class Paperback implements CloverFormat {

    private static final Logger LOG =
            Logger.getLogger(
                    Paperback.class.getName());

    @Inject IssueDimensions dimensions;
    @Inject Image coverArtImage;
    @Inject Instance<Block<Graphics2D>> blocks;
    @Inject CloverProperties cloverProperties;

    private List<Image> images;

    @Override
    public List<Image> getImages() {
        if (images == null) {
            init();
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
        return image ->
                image.withGraphics(graphics2D ->
                    blocks.stream().forEach(block ->
                            block.draw(graphics2D, TypedProperties.create())));
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
