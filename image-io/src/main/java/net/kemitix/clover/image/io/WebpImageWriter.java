package net.kemitix.clover.image.io;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebpImageWriter
        extends AbstractImageIOImageWriter {

    private static final String FORMAT_NAME = "webp";

    @Override
    public boolean accepts(final String format) {
        return FORMAT_NAME.equals(format);
    }

    @Override
    protected String getFormatName() {
        return FORMAT_NAME;
    }

}
