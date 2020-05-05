package net.kemitix.clover.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class CloverService implements Runnable {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverService.class.getName());

    private Issue issue;
    private Instance<CloverFormat> formats;

    public CloverService() {
    }

    @Inject
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
                issue.getIssue(),
                issue.getDate()));
        formats.stream()
                .forEach(CloverFormat::write);
    }
}
