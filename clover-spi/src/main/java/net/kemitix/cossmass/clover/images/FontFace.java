package net.kemitix.cossmass.clover.images;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FontFace {

    private final File font;
    private final int size;
    private final String colour;
    private final String shadowColour;
    private final XY shadowOffset;

    public static FontFace of(
            final File fontFile,
            final int size,
            final String colour,
            final XY shadowOffset
    ) {
        final String shadowColour = getShadowColour(colour);
        return new FontFace(fontFile, size, colour,
                shadowColour, shadowOffset);
    }

    public static FontFace of(
            final File fontFile,
            final int size,
            final String colour
    ) {
        return FontFace.of(fontFile, size, colour, XY.at(0, 0));
    }

    private static String getShadowColour(final String colour) {
        switch (colour) {
            case "white":
            case "yellow":
                return "black";
            default:
                return "white";
        }
    }
}
