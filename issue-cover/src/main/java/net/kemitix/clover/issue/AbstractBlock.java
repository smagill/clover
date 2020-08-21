package net.kemitix.clover.issue;

import lombok.extern.java.Log;
import net.kemitix.clover.spi.Block;
import net.kemitix.clover.spi.Drawable;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.inject.Instance;
import java.awt.*;

@Log
public abstract class AbstractBlock implements Block<Graphics2D> {

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        Drawable.draw(getElements(), drawable, typedProperties);
    }

    protected abstract Instance<? extends Drawable<Graphics2D>> getElements();

    @Override
    public void logInfo(String message) {
        log.info(message);
    }
}
