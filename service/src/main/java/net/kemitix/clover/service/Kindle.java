package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.clover.spi.images.ImageService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class Kindle extends FrontCoverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());
    private Issue issue;
    private ImageService imageService;
    private CloverConfig config;

    public Kindle() {
    }

    @Inject
    protected Kindle(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        this.config = config;
        this.issue = issue;
        this.imageService = imageService;
    }

    @Override
    protected CloverConfig getCloverConfig() {
        return config;
    }

    @Override
    protected Issue getIssue() {
        return issue;
    }

    @Override
    protected ImageService getImageService() {
        return imageService;
    }

    @Override
    protected int getHeight() {
        return config.height();
    }

    @Override
    protected int getWidth() {
        return config.width();
    }

    @Override
    protected int frontPageXOffset() {
        return 0;
    }

    @Override
    protected int getCropYOffset() {
        return issue.getKindleYOffset();
    }

    @Override
    protected int getCropXOffset() {
        return issue.getKindleXOffset();
    }

    @Override
    protected String getName() {
        return "kindle";
    }

    @Override
    protected float writeScale() {
        return 1;
    }
}
