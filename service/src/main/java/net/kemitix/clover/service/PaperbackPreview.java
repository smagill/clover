package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.clover.spi.images.Area;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;
import net.kemitix.clover.spi.images.XY;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;
import java.util.logging.Logger;

@ApplicationScoped
public class PaperbackPreview extends FrontCoverFormat {
    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());

    private Paperback paperback;

    public PaperbackPreview() {
    }

    @Inject
    public PaperbackPreview(Paperback paperback) {
        this.paperback = paperback;
    }

    @Override
    protected String getName() {
        return paperback.getName() + "-preview";
    }

    @Override
    protected float writeScale() {
        return paperback.writeScale();
    }

    @Override
    protected CloverConfig getCloverConfig() {
        return paperback.getCloverConfig();
    }

    @Override
    protected Issue getIssue() {
        return paperback.getIssue();
    }

    @Override
    protected ImageService getImageService() {
        return paperback.getImageService();
    }

    @Override
    protected int getHeight() {
        return paperback.getHeight();
    }

    @Override
    protected int getWidth() {
        return paperback.getWidth();
    }

    @Override
    protected Function<Image, Image> spine() {
        return paperback.spine();
    }

    @Override
    protected Function<Image, Image> backCover() {
        return paperback.backCover()
                .andThen(drawBarcodeSpacer());
    }

    @Override
    protected int getCropYOffset() {
        return paperback.getCropYOffset();
    }

    @Override
    protected int getCropXOffset() {
        return paperback.getCropXOffset();
    }

    private Function<Image, Image> drawBarcodeSpacer() {
        final XY topLeft = XY.at(
                getCloverConfig().getBarcodeLeft(),
                getCloverConfig().getBarcodeTop());
        final Area area = Area.builder()
                .width(getCloverConfig().getBarcodeWidth())
                .height(getCloverConfig().getBarcodeHeight())
                .build();
        final String fillColour = getCloverConfig().getBarcodeFillColour();
        return image ->
                image.withFilledArea(topLeft, area, fillColour);
    }

    @Override
    protected int frontPageXOffset() {
        return paperback.frontPageXOffset();
    }
}
