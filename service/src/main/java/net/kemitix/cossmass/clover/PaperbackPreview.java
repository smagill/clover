package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.ImageService;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class PaperbackPreview extends Paperback {
    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());
    private final Issue issue;

    protected PaperbackPreview(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.issue = issue;
        LOGGER.info("PaperbackPreview");
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
