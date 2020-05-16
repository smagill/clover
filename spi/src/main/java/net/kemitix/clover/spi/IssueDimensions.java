package net.kemitix.clover.spi;

public interface IssueDimensions {
    Region getSpineCrop();

    Region getWrapCrop();

    float getScaleFromOriginal();

    Region getFrontCrop();
}
