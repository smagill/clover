package net.kemitix.clover.spi;

public interface FlipAxis<T> {
    T flipVertically(int axis);

    T flipHorizontally(int axis);
}
