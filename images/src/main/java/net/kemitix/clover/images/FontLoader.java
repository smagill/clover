package net.kemitix.clover.images;

import net.kemitix.clover.spi.images.FontFace;

import java.awt.*;

public interface FontLoader {
    Font loadFont(FontFace fontFace);
}
