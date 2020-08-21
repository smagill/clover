package net.kemitix.clover.issue;

import lombok.Getter;
import lombok.extern.java.Log;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@Log
@FrontCover
@ApplicationScoped
public class CoverLogo extends AbstractElement {

    @Getter private final int priority = 10;

    @Inject CloverProperties cloverProperties;
    @Inject IssueConfig issueConfig;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject IssueDimensions dimensions;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        log.info("Drawing cover logo");
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
