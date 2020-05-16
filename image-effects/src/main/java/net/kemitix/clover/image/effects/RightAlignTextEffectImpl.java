package net.kemitix.clover.image.effects;

import lombok.*;
import net.kemitix.clover.spi.*;
import net.kemitix.clover.spi.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.function.Function;
import java.util.stream.IntStream;

@ApplicationScoped
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RightAlignTextEffectImpl
    extends AbstractTextEffect
        implements RightAlignTextEffect<Graphics2D>,
        TextEffect.RegionNext<Graphics2D>,
        TextEffect.TextNext<Graphics2D>,
        Function<Graphics2D, Graphics2D> {

    private String text;
    @Getter private FontFace fontFace;
    @Getter private Region region;

    @Inject @Getter FontCache fontCache;

    @Override
    public Graphics2D apply(Graphics2D graphics2D) {
        String[] split = text.split("\n");
        IntStream.range(0, split.length)
                .forEach(lineNumber -> {
                    String lineOfText = split[lineNumber];
                    if (lineOfText.length() > 0) {
                        drawText(graphics2D, lineNumber, lineOfText, region.getArea());
                    }
                });
        return graphics2D;
    }

    private void drawText(
            Graphics2D graphics2D,
            int lineCount,
            String line,
            Area area
    ) {
        Rectangle2D stringBounds = getStringBounds(graphics2D, line);
        int top = region.getTop() + ((int) stringBounds.getHeight() * lineCount);
        int left = region.getRight() - (int) stringBounds.getWidth();
        AbstractTextEffect.drawText(line, framing -> XY.at(left, top),
                fontFace, graphics2D, fontCache, area);
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
