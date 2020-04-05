package net.kemitix.clover.spi.images;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Area {

    private final int width;
    private final int height;

    public static Area of(final int width, final int height) {
        return Area.builder()
                .width(width)
                .height(height)
                .build();
    }
}
