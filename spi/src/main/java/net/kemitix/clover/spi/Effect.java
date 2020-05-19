package net.kemitix.clover.spi;

import java.util.function.Consumer;

public interface Effect<T> extends Consumer<T> {
    interface RegionNext<T> { Consumer<T> region(Region region);}
}
