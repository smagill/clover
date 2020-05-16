package net.kemitix.clover.images;

import net.kemitix.clover.spi.Colours;
import net.kemitix.clover.spi.FatalCloverError;
import org.beryx.awt.color.ColorFactory;

import javax.enterprise.context.ApplicationScoped;
import java.awt.*;
import java.util.Optional;

@ApplicationScoped
public class ColoursImpl implements Colours {
    @Override
    public Paint getColor(String name) {
        return Optional.ofNullable(
                ColorFactory.valueOf(name))
                .orElseThrow(() ->
                        new FatalCloverError(
                                "Unknown colour: " + name));

    }
}
