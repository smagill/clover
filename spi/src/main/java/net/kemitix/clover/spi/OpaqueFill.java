package net.kemitix.clover.spi;

public interface OpaqueFill<T> extends Effect<T> {
    ColourNext<T> opacity(double opacity);
    interface ColourNext<T> { Effect.RegionNext<T> colour(String colour);}
}
