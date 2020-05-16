package net.kemitix.clover.image.effects;

import lombok.*;
import net.kemitix.clover.spi.*;
import net.kemitix.clover.spi.Image;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.function.Function;
import java.util.stream.IntStream;

@With
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleTextEffectImage
        extends AbstractTextEffect
        implements SimpleTextEffect<Image>,
        TextEffect.RegionNext<Image>,
        TextEffect.TextNext<Image>,
        Function<Image, Image> {

    @Inject
    @Getter FontCache fontCache;
    @Getter
    FontFace fontFace;
    @Getter Region region;
    @Getter String text;

    @Override
    public Image apply(Image image) {
        return image.withGraphics(graphics2D -> {
            double lineHeight = getStringBounds(graphics2D, "X").getHeight();
            String[] split = text.split("\n");
            IntStream.range(0, split.length)
                    .forEach(lineNumber -> {
                        String lineOfText = split[lineNumber];
                        if (lineOfText.length() > 0) {
                            int lineOffset = (int) lineHeight * lineNumber;
                            drawLineOfText(image, graphics2D, lineOfText, lineOffset);
                        }
                    });
        });
    }

    private void drawLineOfText(
            Image image,
            Graphics2D graphics2D,
            String lineOfText,
            int lineOffset
    ) {
        XY position = XY.at(region.getLeft(), region.getTop() + lineOffset);
        AbstractTextEffect.drawText(lineOfText, framing -> position, fontFace,
                graphics2D, fontCache, image.getArea());
    }

    @Override
    public RegionNext<Image> fontFace(FontFace fontFace) {
        return withFontFace(fontFace);
    }

    @Override
    public TextNext<Image> region(Region region) {
        return withRegion(region);
    }

    @Override
    public Function<Image, Image> text(String text) {
        return withText(text);
    }
}
