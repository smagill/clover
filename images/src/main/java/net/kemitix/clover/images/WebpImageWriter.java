package net.kemitix.clover.images;

import javax.enterprise.context.Dependent;

@Dependent
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
