package net.kemitix.clover.spi;

import lombok.extern.java.Log;

import java.awt.*;

@Log
public abstract class AbstractElement implements Element<Graphics2D> {

    @Override
    public void logInfo(String message) {
        log.info(message);
    }

}
