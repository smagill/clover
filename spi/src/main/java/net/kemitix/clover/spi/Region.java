package net.kemitix.clover.spi;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Rectangle2D;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Region
        implements RotateQuadrant<Region>, FlipAxis<Region> {

    @Builder.Default
    private final int top = 0;
    @Builder.Default
    private final int left = 0;
    private final int width;
    private final int height;

    public static Region from(Rectangle2D rectangle2D) {
        Rectangle bounds = rectangle2D.getBounds();
        return Region.builder()
                .top(bounds.y)
                .left(bounds.x)
                .width(bounds.width)
                .height(bounds.height)
                .build();
    }

    public static Region from(Area area) {
        return Region.builder()
                .top(0).left(0)
                .width((int) area.getWidth())
                .height((int) area.getHeight())
                .build();
    }

    public int getRight() {
        return getLeft() + getWidth();
    }

    public int getBottom() {
        return getTop() + getHeight();
    }

    public void mustContain(final Region inner) {
        mustBeBiggerThan(inner);
        if (!isBetween(inner.getLeft(), getLeft(), getRight())) {
            notContains("left edge is outside", inner);
        }
        if (!isBetween(inner.getRight(), getLeft(), getRight())) {
            notContains("right edge is outside", inner);
        }
        if (!isBetween(inner.getTop(), getTop(), getBottom())) {
            notContains("top edge is outside", inner);
        }
        if (!isBetween(inner.getBottom(), getTop(), getBottom())) {
            notContains("bottom edge is outside", inner);
        }
    }

    public void mustContain(Rectangle2D area) {
        if (area.getWidth() > getWidth()) {
            notContains("is wider than", area);
        }
        if (area.getHeight() > getHeight()) {
            notContains("is taller than", area);
        }
    }

    public void mustBeBiggerThan(Region inner) {
        if (inner.getWidth() > getWidth()) {
            notContains("is wider than", inner);
        }
        if (inner.getHeight() > getHeight()) {
            notContains("is taller than", inner);
        }
    }

    private void notContains(final String message, final Rectangle2D inner) {
        throw new IllegalArgumentException(String.format(
                "Inner %s container:\n" +
                        " Container: %s - right=%d, bottom=%d\n" +
                        "     Inner: %s",
                message,
                this, getRight(), getBottom(),
                inner));
    }

    private void notContains(final String message, final Region inner) {
        throw new IllegalArgumentException(String.format(
                "Inner %s container:\n" +
                        " Container: %s - right=%d, bottom=%d\n" +
                        "     Inner: %s - right=%d, bottom=%d",
                message,
                this, getRight(), getBottom(),
                inner, inner.getRight(), inner.getBottom()));
    }

    private boolean isBetween(
            final int subject,
            final int lower,
            final int upper
    ) {
        return lower <= subject && subject <= upper;
    }

    @Override
    public String toString() {
        return "Region{" +
                "(left=" + left +
                ", right=" + getRight() +
                "), (top=" + top +
                ", bottom=" + getBottom() +
                "), width=" + width +
                ", height=" + height +
                '}';
    }

    public Region withPadding(int padding) {
        return Region.builder()
                .top(top + padding)
                .left(left + padding)
                .width(width - (2 * padding))
                .height(height - (2 * padding))
                .build();
    }

    public Area getArea() {
        return Area.builder()
                .width(width)
                .height(height)
                .build();
    }

    public Region transposed() {
        return Region.builder()
                .top(left).left(top)
                .width(height).height(width)
                .build();
    }

    public Region rotateCW() {
        return Region.builder()
                .top(left).left(-(top + height))
                .width(height).height(width)
                .build();
    }
    public Region rotateCCW() {
        return Region.builder()
                .top(-(left + width)).left(this.top)
                .width(height).height(width)
                .build();
    }

    public Region flipVertically(int axis) {
        return toBuilder().top((axis - top) + axis)
                .build();
    }

    public Region flipHorizontally(int axis) {
        return toBuilder().left((axis - left) + axis)
                .build();
    }

    public Region withOffset(int x, int y) {
        return toBuilder()
                .left(left + y)
                .top(top + x)
                .build();
    }
}
