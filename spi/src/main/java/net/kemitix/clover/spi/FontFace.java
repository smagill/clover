package net.kemitix.clover.spi;

import java.io.File;

public interface FontFace {
    static FontFace of(
            File fontFile,
            int size,
            String colour,
            XY shadowOffset
    ) {
        final String shadowColour = FontFaceImpl.shadowColour(colour);
        return new FontFaceImpl(fontFile, size, colour,
                shadowColour, shadowOffset);
    }

    static FontFace of(
            File fontFile,
            int size,
            String colour
    ) {
        return of(fontFile, size, colour, XY.at(0, 0));
    }

    File getFont();

    int getSize();

    String getColour();

    String getShadowColour();

    XY getShadowOffset();
}
