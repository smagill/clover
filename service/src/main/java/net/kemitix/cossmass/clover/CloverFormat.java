package net.kemitix.cossmass.clover;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

public abstract class CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverFormat.class.getName());

    private final CloverConfig config;

    protected CloverFormat(final CloverConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void create() {
        LOGGER.info("create()");
    }

    public void write() {
        LOGGER.info("write()");
    }
}
