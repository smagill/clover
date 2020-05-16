package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverFormat;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.Image;
import net.kemitix.clover.spi.Region;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class PaperbackPreview implements CloverFormat {

    @Inject CloverProperties cloverProperties;
    @Inject Paperback paperback;

    @Override
    public List<Image> getImages() {
        return paperback.getImages()
                .stream()
                .map(drawBarcodeSpacer())
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return paperback.getName() + "-preview";
    }

    @Override
    public TypedProperties getImageProperties() {
        return paperback.getImageProperties();
    }

    @Override
    public boolean isEnabled() {
        return cloverProperties.isEnablePaperbackPreview();
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
