package net.kemitix.clover.image.effects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.kemitix.clover.spi.FontCache;
import net.kemitix.clover.spi.RightAlignTextEffect;
import net.kemitix.clover.spi.TextEffect;
import net.kemitix.clover.spi.images.*;
import net.kemitix.clover.spi.images.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.function.Function;
import java.util.stream.IntStream;

@ApplicationScoped
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RightAlignTextEffectImpl
    extends AbstractTextEffect
        implements RightAlignTextEffect,
        TextEffect.RegionNext,
        TextEffect.TextNext,
        Function<Image, Image> {

    private String text;
    @Getter
    private FontCache fontCache;
    @Getter
    private FontFace fontFace;
    @Getter
    private Region region;

    public RightAlignTextEffectImpl() {
    }

    @Inject
    public RightAlignTextEffectImpl(FontCache fontCache) {
        this.fontCache = fontCache;
    }

    @Override
    public Image apply(Image image) {
        return image.withGraphics(graphics2d -> {
            String[] split = text.split("\n");
            IntStream.range(0, split.length)
                    .forEach(lineNumber -> {
                        String lineOfText = split[lineNumber];
                        if (lineOfText.length() > 0) {
                            drawText(graphics2d, lineNumber, lineOfText, image.getArea());
                        }
                    });
        });
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
    public RegionNext fontFace(FontFace fontFace) {
        return toBuilder().fontFace(fontFace).build();
    }

    @Override
    public TextNext region(Region region) {
        return toBuilder().region(region).build();
    }

    @Override
    public Function<Image, Image> text(String text) {
        return toBuilder().text(text).build();
    }
}
