package net.kemitix.cossmass.clover;

import javax.enterprise.context.Dependent;
import java.util.logging.Logger;

@Dependent
public class PaperbackPreview extends Paperback {
    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());

    protected PaperbackPreview(final CloverConfig config) {
        super(config);
        LOGGER.info("PaperbackPreview");
    }
}
