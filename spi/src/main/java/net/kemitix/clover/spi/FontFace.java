package net.kemitix.clover.spi;

import java.net.URI;

public interface FontFace {
    static FontFace of(
            URI fontUri,
            int size,
            String colour,
            XY shadowOffset
    ) {
        final String shadowColour = FontFaceImpl.shadowColour(colour);
        return new FontFaceImpl(fontUri, size, colour,
                shadowColour, shadowOffset);
    }

    static FontFace of(
            URI fontUri,
            int size,
            String colour
    ) {
        return of(fontUri, size, colour, XY.at(0, 0));
    }

    URI getFontLocation();

    int getSize();

    String getColour();

    String getShadowColour();

    XY getShadowOffset();

    FontFace withSize(int size);
    FontFace withColour(String colour);
    FontFace withShadowColour(String colour);
    FontFace withShadowOffset(XY offset);
}
