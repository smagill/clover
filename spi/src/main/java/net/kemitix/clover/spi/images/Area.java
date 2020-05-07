package net.kemitix.clover.spi.images;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Area {

    private final float width;
    private final float height;

    public static Area of(final float width, final float height) {
        return Area.builder()
                .width(width)
                .height(height)
                .build();
    }
}
