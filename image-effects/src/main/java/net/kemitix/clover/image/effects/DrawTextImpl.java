package net.kemitix.clover.image.effects;

import net.kemitix.clover.spi.*;
import org.beryx.awt.color.ColorFactory;

import javax.enterprise.context.ApplicationScoped;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class DrawTextImpl implements DrawText {

    @Override
    public void draw(
            final String text,
            final Function<Framing, XY> positioning,
            final FontFace fontFace,
            final Graphics2D graphics,
            final FontCache fontCache,
            final Area imageArea
    ) {
        final Font font = fontCache.loadFont(fontFace);
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        graphics.setFont(font);
        final Rectangle2D stringBounds =
                font.getStringBounds(text, graphics.getFontRenderContext());
        final XY topLeft = positioning.apply(Framing.builder()
                .outer(imageArea)
                .inner(Area.of(((int) stringBounds.getWidth()), ((int) stringBounds.getHeight())))
                .build());
        // Drop Shadow
        final XY shadowOffset = fontFace.getShadowOffset();
        if (shadowOffset.getX() != 0 || shadowOffset.getY() != 0) {
            graphics.setPaint(getColor(fontFace.getShadowColour()));
            graphics.drawString(text,
                    topLeft.getX() + shadowOffset.getX(),
                    (int) (topLeft.getY() - stringBounds.getY() + shadowOffset.getY()));
        }
        // Text
        graphics.setPaint(getColor(fontFace.getColour()));
        graphics.drawString(text,
                topLeft.getX(),
                (int) (topLeft.getY() - stringBounds.getY()));


    }

    static Color getColor(final String colour) {
        return Optional.ofNullable(
                ColorFactory.valueOf(colour))
                .orElseThrow(() ->
                        new FatalCloverError(
                                "Unknown colour: " + colour));
    }

}
