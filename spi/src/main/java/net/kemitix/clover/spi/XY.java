package net.kemitix.clover.spi;

import lombok.*;

import java.util.function.Function;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class XY
        implements RotateQuadrant<XY>, FlipAxis<XY> {

    private final int x;
    private final int y;

    public static XY at(final int x, final int y) {
        return new XY(x, y);
    }

    public XY map(final Function<XY, XY> f) {
        return f.apply(this);
    }

    @Override
    public XY rotateCW() {
        return XY.at(-y, x);
    }

    @Override
    public XY rotateCCW() {
        return XY.at(y, -x);
    }

    @Override
    public XY flipVertically(int axis) {
        return XY.at(x, (axis - y) + axis);
    }

    @Override
    public XY flipHorizontally(int axis) {
        return XY.at((axis - x) + axis, y);
    }
}
