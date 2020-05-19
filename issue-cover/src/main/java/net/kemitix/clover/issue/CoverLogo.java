package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@FrontCover
@ApplicationScoped
public class CoverLogo implements Element<Graphics2D> {

    @Getter private final int priority = 10;

    @Inject CloverProperties cloverProperties;
    @Inject IssueConfig issueConfig;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject IssueDimensions dimensions;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        FontFace fontFace = FontFace.of(
                cloverProperties.getFontLocation(),
                217,
                issueConfig.getTitleColour(),
                XY.at(
                        cloverProperties.getDropShadowXOffset(),
                        cloverProperties.getDropShadowYOffset()));
        var text = String.join("\n",
                issueConfig.getPublicationTitle().split(" "));
        simpleTextEffect
                .fontFace(fontFace)
                .text(text)
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.CENTRE)
                .region(dimensions.getFrontCrop().withPadding(85))
                .accept(drawable);
    }
}
