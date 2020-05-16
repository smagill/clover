package net.kemitix.clover.spi;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;

@Getter
@Builder
@ToString
public class Area
        implements RotateQuadrant<Area> {

    private final float width;
    private final float height;

    public static Area of(final float width, final float height) {
        return Area.builder()
                .width(width)
                .height(height)
                .build();
    }

    public Area transposed() {
        return Area.of(height, width);
    }

    @Override
    public Area rotateCW() {
        return transposed();
    }

    @Override
    public Area rotateCCW() {
        return transposed();
    }
}
