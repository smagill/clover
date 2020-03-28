package net.kemitix.cossmass.clover;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import java.util.logging.Logger;

@Dependent
public class CloverService implements Runnable {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverService.class.getName());

    private final Issue issue;
    private final Instance<CloverFormat> formats;

    public CloverService(
            final Issue issue,
            @Any final Instance<CloverFormat> formats
    ) {
        this.formats = formats;
        this.issue = issue;
    }

    @Override
    public void run() {
        LOGGER.info(String.format("Clover - Issue %s - %s",
                issue.issue,
                issue.date));
        formats.stream().forEach(CloverFormat::write);
    }
}
