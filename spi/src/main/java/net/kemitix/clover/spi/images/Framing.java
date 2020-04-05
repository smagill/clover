package net.kemitix.clover.spi.images;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.kemitix.clover.spi.FatalCloverError;

@Getter
@Builder(toBuilder = true)
@ToString
public class Framing {
    private final Area outer;
    private final Area inner;

    public static Framing of(final Area outer, final Area inner) {
        return Framing.builder()
                .outer(outer)
                .inner(inner)
                .build();
    }

    public XY centered() {
        if (inner.getHeight() > outer.getHeight()) {
            throw new FatalCloverError(String.format("Inner is taller than outer: outer=%s, inner=%s", outer, inner));
        }
        if (inner.getWidth() > outer.getWidth()) {
            throw new FatalCloverError(String.format("Inner is wider than outer: outer=%s, inner=%s", outer, inner));
        }
        final int x = (outer.getWidth() - inner.getWidth()) / 2;
        final int y = (outer.getHeight() - inner.getHeight()) / 2;
        return XY.at(x, y);
    }
}
