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

    protected Kindle(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.issue = issue;
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
}
