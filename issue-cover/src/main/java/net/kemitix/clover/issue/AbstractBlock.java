package net.kemitix.clover.issue;

import net.kemitix.clover.spi.Block;
import net.kemitix.clover.spi.Drawable;

import javax.enterprise.inject.Instance;
import java.awt.*;

public abstract class AbstractBlock implements Block<Graphics2D> {

    @Override
    public void draw(Graphics2D drawable) {
        Drawable.draw(getElements(), drawable);
    }

    protected abstract Instance<? extends Drawable<Graphics2D>> getElements();

}
