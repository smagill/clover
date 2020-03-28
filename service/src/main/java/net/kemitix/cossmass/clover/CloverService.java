package net.kemitix.cossmass.clover;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class CloverService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverService.class.getName());

    public void run() {
        LOGGER.info("Running...");
    }
}
