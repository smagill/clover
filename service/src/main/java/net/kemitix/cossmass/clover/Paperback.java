package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.ImageService;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class Paperback extends CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Paperback.class.getName());
    private final Issue issue;

    protected Paperback(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.issue = issue;
        LOGGER.info("Paperback");
    }

    @Override
    protected int getCropYOffset() {
        return issue.paperbackYOffset;
    }

    @Override
    protected int getCropXOffset() {
        return issue.paperbackXOffset;
    }
}
