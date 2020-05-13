package net.kemitix.clover.spi.images;

public interface FlipAxis<T> {
    T flipVertically(int axis);

    T flipHorizontally(int axis);
}
