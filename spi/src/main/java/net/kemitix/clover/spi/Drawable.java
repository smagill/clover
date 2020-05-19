package net.kemitix.clover.spi;

import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.inject.Instance;
import java.awt.*;

import static net.kemitix.clover.spi.Prioritised.byPriority;

public interface Drawable<T> extends Prioritised  {
    void draw(T drawable, TypedProperties typedProperties);

    static void draw(
            Instance<? extends Drawable<Graphics2D>> drawables,
            Graphics2D graphics2D,
            TypedProperties typedProperties
    ) {
        drawables.stream()
                .sorted(byPriority())
                .forEach(element -> element.draw(graphics2D, typedProperties));
    }

}
