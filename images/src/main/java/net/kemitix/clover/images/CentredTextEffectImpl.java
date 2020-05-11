package net.kemitix.clover.images;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import net.kemitix.clover.spi.CenteredTextEffect;
import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.images.*;
import net.kemitix.clover.spi.images.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.function.Function;

@ApplicationScoped
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CentredTextEffectImpl
        implements CenteredTextEffect,
        CenteredTextEffect.TextNext,
        CenteredTextEffect.RegionNext,
        Function<Image, Image> {

    private FontCache fontCache;
    private String text;
    private FontFace fontFace;
    private Region region;

    public CentredTextEffectImpl() {
    }

    @Inject
    public CentredTextEffectImpl(FontCache fontCache) {
        this.fontCache = fontCache;
    }

    @Override
    public Image apply(Image image) {
        return image.withGraphics(graphics2d -> {
            int lineCount = 0;
            System.out.println("text = " + text);
            for (String line : text.split("\n")) {
                drawText(image, graphics2d, lineCount, line);
                lineCount++;
            }
        });
    }

    private void drawText(Image image, Graphics2D graphics2d, int lineCount, String line) {
        int lineHeight = (int) getStringBounds(graphics2d, line).getHeight();
        CloverImage.drawText(line, framing -> {
                    XY centered = framing.toBuilder()
                            .outer(region.getArea())
                            .inner(Area.of(getTextWidth(graphics2d, line), region.getHeight()))
                            .build().centered();
                    return XY.at(centered.getX() + region.getLeft(),
                            centered.getY() + region.getTop() + (lineHeight * lineCount));
                },
                fontFace, graphics2d, fontCache, image.getBufferedImage());
    }

    private int getTextWidth(Graphics2D graphics2d, String text) {
        Rectangle2D stringBounds = getStringBounds(graphics2d, text);
        return (int) stringBounds.getWidth();
    }

    private Rectangle2D getStringBounds(Graphics2D graphics2d, String text) {
        Font font = fontCache.loadFont(fontFace);
        FontRenderContext fontRenderContext = graphics2d.getFontRenderContext();
        Rectangle2D stringBounds = font.getStringBounds(text, fontRenderContext);
        region.mustContain(stringBounds);
        return stringBounds;
    }

    @Override
    public Function<Image, Image> text(String text) {
        return toBuilder().text(text).build();
    }

    @Override
    public RegionNext fontFace(FontFace fontFace) {
        return toBuilder().fontFace(fontFace).build();
    }

    @Override
    public TextNext region(Region region) {
        return toBuilder().region(region).build();
    }

}
