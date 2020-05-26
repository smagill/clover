package net.kemitix.clover.image.effects;

import lombok.*;
import net.kemitix.clover.spi.*;
import net.kemitix.clover.spi.DrawText;
import net.kemitix.text.fit.BoxFitter;
import net.kemitix.text.fit.WordWrapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

@ApplicationScoped
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SimpleTextEffectImpl
        implements
        SimpleTextEffect<Graphics2D>,
        TextEffect.TextNext<Graphics2D>,
        TextEffect.RegionNext<Graphics2D>,
        TextEffect.HAlignNext<Graphics2D>,
        TextEffect.VAlignNext<Graphics2D>,
        TextEffect.WrapFitOrTextNext<Graphics2D>,
        TextEffect<Graphics2D> {

    @Inject @Getter FontCache fontCache;
    @Inject WordWrapper wordWrapper;
    @Inject BoxFitter boxFitter;
    @Inject DrawText drawText;

    @Getter Region region;

    VAlignment vAlignment;
    HAlignment hAlignment;
    FontFace fontFace;
    String text;
    boolean wrap = false;
    boolean fit = false;

    @Override
    public void accept(Graphics2D graphics2D) {
        Function<Integer, Font> fontFactory = size ->
                fontCache.loadFont(fontFace.withSize(size));
        int size = getFit(graphics2D, fontFactory);
        Font font = fontCache.loadFont(fontFace.withSize(size));
        List<String> split = getWrap(graphics2D, font);
        int top = topEdge(split.size() * size);
        IntStream.range(0, split.size())
                .forEach(lineNumber -> {
                    String lineOfText = split.get(lineNumber);
                    if (lineOfText.length() > 0) {
                        drawText(graphics2D, lineNumber, lineOfText,
                                region.getArea(), fontFace.withSize(size), top);
                    }
                });
    }

    private int getFit(
            Graphics2D graphics2D,
            Function<Integer, Font> fontFactory
    ) {
        if (fit) {
            return boxFitter.fit(text, fontFactory, graphics2D,
                    new Rectangle(region.getLeft(), region.getTop(),
                            region.getWidth(), region.getHeight()));
        }
        return fontFace.getSize();
    }

    private List<String> getWrap(
            Graphics2D graphics2D,
            Font font
    ) {
        if (wrap) {
            return wordWrapper.wrap(text, font, graphics2D, region.getWidth());
        }
        return Arrays.asList(text.split("\n"));
    }

    private void drawText(
            Graphics2D graphics2d,
            int lineCount,
            String line,
            Area imageArea,
            FontFace face,
            int topOffset
    ) {
        Font font = fontCache.loadFont(face);
        Rectangle2D stringBounds =
                font.getStringBounds(line, graphics2d.getFontRenderContext());
        int top = getTop(lineCount, topOffset, (int) stringBounds.getHeight());
        int left = lineLeftEdge((int) stringBounds.getWidth());
        drawText.draw(line, framing -> XY.at(left, top),
                face, graphics2d, fontCache, imageArea);
        //graphics2d.drawRect(left, top, (int) stringBounds.getWidth(), (int) stringBounds.getHeight());
    }

    private int getTop(int lineCount, int topOffset, int lineHeight) {
        return topOffset +
                (lineHeight * lineCount);
    }

    private int topEdge(int height) {
        switch (vAlignment) {
            case TOP:
                return region.getTop();
            case BOTTOM:
                return region.getTop() + (region.getHeight() - height);
            case CENTRE:
                return region.getTop() + ((region.getHeight() - height) / 2);
            default:
                throw new UnsupportedOperationException(
                        "Unknown Vertical Alignment: " + hAlignment);
        }
    }

    private int lineLeftEdge(int width) {
        switch (hAlignment) {
            case LEFT:
                return region.getLeft();
            case RIGHT:
                return region.getLeft() + region.getWidth() - width;
            case CENTRE:
                return region.getLeft() + ((region.getWidth() - width) / 2);
            default:
                throw new UnsupportedOperationException(
                        "Unknown Horizontal Alignment: " + hAlignment);
        }
    }

    @Override
    public VAlignNext<Graphics2D> text(String text) {
        return withText(text);
    }

    @Override
    public WrapFitOrTextNext<Graphics2D> fontFace(FontFace fontFace) {
        return withFontFace(fontFace);
    }

    @Override
    public Consumer<Graphics2D> region(Region region) {
        return withRegion(region);
    }

    @Override
    public RegionNext<Graphics2D> hAlign(HAlignment hAlignment) {
        return withHAlignment(hAlignment);
    }

    @Override
    public HAlignNext<Graphics2D> vAlign(VAlignment vAlignment) {
        return withVAlignment(vAlignment);
    }

    @Override
    public TextNext<Graphics2D> wrap() {
        return withWrap(true);
    }

    @Override
    public TextNext<Graphics2D> fit() {
        return withFit(true).wrap();
    }
}
