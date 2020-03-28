package net.kemitix.cossmass.clover;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class CloverMain {

    private final CloverService service;

    public CloverMain(final CloverService service) {
        this.service = service;
    }

    void onStart(@Observes final StartupEvent ev) {
        new Thread(() -> {
            service.run();
            System.exit(0);
        }).start();
    }

}
