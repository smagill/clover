package net.kemitix.clover;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import net.kemitix.clover.service.CloverService;
import javax.inject.Inject;

@QuarkusMain
public class CloverMain implements QuarkusApplication {

    @Inject
    CloverService service;

    @Override
    public int run(String... args) {
        service.run();
        return 0;
    }
}
