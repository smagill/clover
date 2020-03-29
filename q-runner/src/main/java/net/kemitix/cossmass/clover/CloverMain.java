package net.kemitix.cossmass.clover;

import io.quarkus.runtime.StartupEvent;
import net.kemitix.cossmass.clover.images.ImageService;
import net.kemitix.cossmass.clover.images.imglib.ImgLibImageService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CloverMain {

    private final CloverService service;

    public CloverMain(final CloverService service) {
        this.service = service;
    }

    @Produces
    ImageService imageService() {
        return new ImgLibImageService();
    }

    void onStart(@Observes final StartupEvent ev) {
        new Thread(() -> {
            try {
                service.run();
            } finally {
                System.exit(0);
            }
        }).start();
    }

}
