package net.kemitix.clover.story.card;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@StoryCard
@ApplicationScoped
public class CardLogo implements Element<Graphics2D> {

    @Getter private final int priority = 10;

    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject CloverProperties cloverProperties;
    @Inject StoryCardProperties properties;
    @Inject IssueConfig issueConfig;
    @Inject StoryCardDimensions dimensions;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        Region region = dimensions.getLogoRegion();
        simpleTextEffect
                .fontFace(fontFace())
                .text(text())
                .vAlign(TextEffect.VAlignment.CENTRE)
                .hAlign(TextEffect.HAlignment.CENTRE)
                .region(region)
                .accept(drawable);

//        drawable.drawRect(region.getLeft(), region.getTop(),
//                region.getWidth(), region.getHeight());
    }

    private String text() {
        return String.join("\n",
                issueConfig.getPublicationTitle().split(" "));
    }

    private FontFace fontFace() {
        return FontFace.of(
                cloverProperties.getFontLocation(),
                properties.getLogoFontSize(),
                issueConfig.getTitleColour(),
                XY.at(
                        cloverProperties.getDropShadowXOffset(),
                        cloverProperties.getDropShadowYOffset()));
    }
}
