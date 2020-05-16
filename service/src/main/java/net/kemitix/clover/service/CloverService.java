package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class CloverService implements Runnable {

    @Inject Instance<CloverFormat> formats;
    @Inject FormatWriter formatWriter;

    @Override
    public void run() {
        formats.stream()
                .filter(CloverFormat::isEnabled)
                .forEach(format -> formatWriter.write(format));
    }
}
