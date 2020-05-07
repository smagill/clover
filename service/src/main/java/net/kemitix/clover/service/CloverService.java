package net.kemitix.clover.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class CloverService implements Runnable {

    private Instance<CloverFormat> formats;
    private FormatWriter formatWriter;

    public CloverService() {
    }

    @Inject
    public CloverService(
            final FormatWriter formatWriter,
            @Any final Instance<CloverFormat> formats
    ) {
        this.formatWriter = formatWriter;
        this.formats = formats;
    }

    @Override
    public void run() {
        formats.stream()
                .forEach(format -> formatWriter.write(format));
    }
}
