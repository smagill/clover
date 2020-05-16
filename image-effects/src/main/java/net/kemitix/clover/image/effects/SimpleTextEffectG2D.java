package net.kemitix.clover.image.effects;

import lombok.*;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.function.Function;
import java.util.stream.IntStream;

@With
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleTextEffectG2D
        extends AbstractTextEffect
        implements SimpleTextEffect<Graphics2D>,
        TextEffect.RegionNext<Graphics2D>,
        TextEffect.TextNext<Graphics2D>,
        Function<Graphics2D, Graphics2D> {

    @Inject
    @Getter
    FontCache fontCache;
    @Getter
    FontFace fontFace;
    @Getter
    Region region;
    @Getter String text;

    @Override
    public Graphics2D apply(Graphics2D graphics2D) {
        double lineHeight = getStringBounds(graphics2D, "X").getHeight();
        String[] split = text.split("\n");
        IntStream.range(0, split.length)
                .forEach(lineNumber -> {
                    String lineOfText = split[lineNumber];
                    if (lineOfText.length() > 0) {
                        int lineOffset = (int) lineHeight * lineNumber;
                        drawLineOfText(graphics2D, lineOfText, lineOffset);
                    }
                });
        return graphics2D;
    }

    private void drawLineOfText(Graphics2D graphics2D, String lineOfText, int lineOffset) {
        XY position = XY.at(region.getLeft(), region.getTop() + lineOffset);
        AbstractTextEffect.drawText(lineOfText, framing -> position, fontFace,
                graphics2D, fontCache, region.getArea());
    }

    @Override
    public RegionNext<Graphics2D> fontFace(FontFace fontFace) {
        return withFontFace(fontFace);
    }

    @Override
    public TextNext<Graphics2D> region(Region region) {
        return withRegion(region);
    }

    @Override
    public Function<Graphics2D, Graphics2D> text(String text) {
        return withText(text);
    }
}
