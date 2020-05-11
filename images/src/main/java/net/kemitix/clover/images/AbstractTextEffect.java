package net.kemitix.clover.images;

import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Region;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

abstract class AbstractTextEffect {

    private Font getFont() {
        return getFontCache().loadFont(getFontFace());
    }

    protected abstract FontFace getFontFace();

    protected abstract FontCache getFontCache();

    protected Rectangle2D getStringBounds(Graphics2D graphics2d, String text) {
        Font font = getFont();
        FontRenderContext fontRenderContext = graphics2d.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(text, fontRenderContext);
        getRegion().mustContain(stringBounds);
        return stringBounds;
    }

    protected abstract Region getRegion();
}
