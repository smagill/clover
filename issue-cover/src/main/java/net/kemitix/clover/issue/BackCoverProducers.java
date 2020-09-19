package net.kemitix.clover.issue;

import net.kemitix.clover.spi.*;
import net.kemitix.fontface.FontFace;

import javax.enterprise.inject.Produces;

public class BackCoverProducers {

    @Produces @BackCover
    FontFace backCoverFontFace(
            CloverProperties cloverProperties,
            IssueConfig issueConfig
    ) {
        return FontFace.of(
                cloverProperties.getFontLocation(),
                48,
                issueConfig.getTextColour(),
                cloverProperties.getDropShadowXOffset(),
                cloverProperties.getDropShadowYOffset());
    }
}
