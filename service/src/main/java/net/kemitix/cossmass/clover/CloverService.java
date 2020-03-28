package net.kemitix.cossmass.clover;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class CloverService implements Runnable {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverService.class.getName());

    private final CloverConfig config;
    private final Issue issue;

    public CloverService(
            final CloverConfig config,
            final Issue issue
    ) {
        this.config = config;
        this.issue = issue;
    }

    @Override
    public void run() {
        LOGGER.info("Running...");
        LOGGER.info("IssueDir: " + config.issueDir);
        LOGGER.info("Page Width: " + config.width);
        LOGGER.info("Page Height: " + config.height);
        LOGGER.info("Inches to PX: " + config.inchesToPX);
        LOGGER.info("Drop Shadow X Offset: " + config.dropShadowXOffset);
        LOGGER.info("Drop Shadow Y Offset: " + config.dropShadowYOffset);
        LOGGER.info("Image Types: " +
                String.join(", ", config.getImageTypes()));
        LOGGER.info("Issue.issue: " + issue.issue);
    }
}
