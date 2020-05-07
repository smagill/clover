package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.Region;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;

@ApplicationScoped
public class PaperbackPreview implements CloverFormat {

    private CloverProperties cloverProperties;
    private Paperback paperback;

    public PaperbackPreview() {
    }

    @Inject
    public PaperbackPreview(
            final CloverProperties cloverProperties,
            final Paperback paperback
    ) {
        this.cloverProperties = cloverProperties;
        this.paperback = paperback;
    }

    @Override
    public Image getImage() {
        return drawBarcodeSpacer()
                .apply(paperback.getImage());
    }

    @Override
    public String getName() {
        return paperback.getName() + "-preview";
    }

    @Override
    public TypedProperties getImageProperties() {
        return paperback.getImageProperties();
    }

    private Function<Image, Image> drawBarcodeSpacer() {
        final Region region = Region.builder()
                .top(cloverProperties.getBarcodeTop())
                .left(cloverProperties.getBarcodeLeft())
                .width(cloverProperties.getBarcodeWidth())
                .height(cloverProperties.getBarcodeHeight())
                .build();
        final String fillColour = cloverProperties.getBarcodeFillColour();
        return image -> image.withFilledArea(region, fillColour);
    }

}
