package net.kemitix.clover.spi;

public interface IssueCrop {
    default Region getRegion() {
        return Region.builder()
                .top(getTop()).left(getLeft())
                .width(getWidth()).height(getHeight())
                .build();
    }

    int getHeight();

    int getWidth();

    int getLeft();

    int getTop();

}
