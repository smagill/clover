package net.kemitix.clover.image.io;

import net.kemitix.clover.spi.CloverProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class WebpImageWriter
        extends AbstractImageIOImageWriter {

    private static final String FORMAT_NAME = "webp";

    @Inject CloverProperties cloverProperties;

    @Override
    public boolean accepts(final String format) {
        return FORMAT_NAME.equals(format);
    }

    @Override
    public boolean isEnabled() {
        return cloverProperties.isEnableWebp();
    }

    @Override
    protected String getFormatName() {
        return FORMAT_NAME;
    }

}
