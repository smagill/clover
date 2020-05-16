package net.kemitix.clover.spi;

import javax.enterprise.inject.Instance;
import java.awt.*;

import static net.kemitix.clover.spi.Prioritised.byPriority;

public interface Drawable<T> extends Prioritised  {
    void draw(T drawable);

    static void draw(
            Instance<? extends Drawable<Graphics2D>> drawables,
            Graphics2D graphics2D
    ) {
        drawables.stream()
                .sorted(byPriority())
                .forEach(element -> element.draw(graphics2D));
    }

}
