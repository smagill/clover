package net.kemitix.clover.spi;

import java.awt.*;

public interface Colours {
    Paint getColor(String name);

    Paint getColor(String name, double opacity);
}
