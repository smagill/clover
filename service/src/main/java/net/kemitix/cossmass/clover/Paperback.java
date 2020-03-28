package net.kemitix.cossmass.clover;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class Paperback extends CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Paperback.class.getName());

    protected Paperback(final CloverConfig config) {
        super(config);
        LOGGER.info("Paperback");
    }

}
