package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.clover.spi.images.Area;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;
import net.kemitix.clover.spi.images.XY;

import javax.enterprise.context.Dependent;
import java.util.function.Function;
import java.util.logging.Logger;

@Dependent
public class PaperbackPreview extends Paperback {
    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());
    private final CloverConfig config;

    protected PaperbackPreview(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService,
            final StoryListFormatter storyListFormatter
    ) {
        super(config, issue, imageService, storyListFormatter);
        this.config = config;
    }

    @Override
    protected String getName() {
        return super.getName() + "-preview";
    }

    @Override
    protected Function<Image, Image> backCover() {
        return super.backCover()
                .andThen(drawBarcodeSpacer());
    }

    private Function<Image, Image> drawBarcodeSpacer() {
        final XY topLeft = XY.at(
                config.getBarcodeLeft(),
                config.getBarcodeTop());
        final Area area = Area.builder()
                .width(config.getBarcodeWidth())
                .height(config.getBarcodeHeight())
                .build();
        final String fillColour = config.getBarcodeFillColour();
        return image ->
                image.withFilledArea(topLeft, area, fillColour);
    }
}
