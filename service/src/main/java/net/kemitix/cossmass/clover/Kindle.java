package net.kemitix.cossmass.clover;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class Kindle extends CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());

    protected Kindle(final CloverConfig config) {
        super(config);
        LOGGER.info("Kindle");
    }
}
