package net.kemitix.clover.image.effects;

import lombok.*;
import net.kemitix.clover.spi.*;
import net.kemitix.clover.spi.DrawText;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@With
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RotatedCenteredTextEffectImpl
        implements RotatedCenteredTextEffect<Graphics2D>,
        TextEffect.RegionNext<Graphics2D>,
        TextEffect.TextNext<Graphics2D>,
        TextEffect.HAlignNext<Graphics2D>,
        TextEffect.VAlignNext<Graphics2D> {

    @Inject @Getter FontCache fontCache;
    @Inject DrawText drawText;

    VAlignment VAlignment;
    HAlignment HAlignment;

    @Getter FontFace fontFace;
    @Getter Region region;
    @Getter String text;

    @Override
    public void accept(Graphics2D graphics2d) {
        graphics2d.setTransform(
                AffineTransform.getQuadrantRotateInstance(1));
        String[] split = text.split("\n");
        IntStream.range(0, split.length)
                .forEach(lineNumber -> {
                    String lineOfText = split[lineNumber];
                    if (lineOfText.length() > 0) {
                        drawText(graphics2d, lineNumber, lineOfText,
                                region.getArea().rotateCW(),
                                region
                                        .rotateCW()
                                        .flipVertically(0)
                        );
                    }
                });
    }

    private void drawText(
            Graphics2D graphics2d,
            int lineCount,
            String line,
            Area imageArea,
            Region region
    ) {
        Font font = fontCache.loadFont(fontFace);
        Rectangle2D stringBounds =
                font.getStringBounds(line, graphics2d.getFontRenderContext());
        int top = getTop(lineCount, region, (int) stringBounds.getHeight());
        int left = getLeft(region, (int) stringBounds.getWidth());
        drawText.draw(line, framing -> XY.at(left, top),
                fontFace, graphics2d, fontCache, imageArea);
    }

    private int getLeft(Region region, int lineWidth) {
        return region.getWidth()
                + region.getLeft()
                + ((region.getWidth() - lineWidth) / 2);
    }

    private int getTop(int lineCount, Region region, int lineHeight) {
        return region.getTop()
                + (lineHeight * lineCount)
                - region.getHeight();
    }

    @Override
    public TextNext<Graphics2D> fontFace(FontFace fontFace) {
        return withFontFace(fontFace);
    }

    @Override
    public Consumer<Graphics2D> region(Region region) {
        return withRegion(region);
    }

    @Override
    public VAlignNext<Graphics2D> text(String text) {
        return withText(text);
    }

    @Override
    public HAlignNext<Graphics2D> vAlign(VAlignment VAlignment) {
        return withVAlignment(VAlignment);
    }

    @Override
    public RegionNext<Graphics2D> hAlign(HAlignment HAlignment) {
        return withHAlignment(HAlignment);
    }
}
