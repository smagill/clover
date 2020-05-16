package net.kemitix.clover.issue;

import net.kemitix.clover.spi.*;

import javax.enterprise.inject.Produces;

public class BackCoverProducers {

    @Produces @BackCover
    FontFace backCoverFontFace(
            CloverProperties cloverProperties,
            IssueConfig issueConfig
    ) {
        return FontFace.of(
                cloverProperties.getFontFile(),
                48,
                issueConfig.getTextColour(),
                XY.at(
                        cloverProperties.getDropShadowXOffset(),
                        cloverProperties.getDropShadowYOffset()));
    }
}
