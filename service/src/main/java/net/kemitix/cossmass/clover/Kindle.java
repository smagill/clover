package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.ImageService;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class Kindle extends FrontCoverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());
    private final Issue issue;
    private final CloverConfig config;

    protected Kindle(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.config = config;
        this.issue = issue;
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
