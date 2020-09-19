package net.kemitix.clover.issue;

import net.kemitix.clover.spi.*;
import net.kemitix.fontface.FontFace;

import javax.enterprise.inject.Produces;
import java.awt.*;
import java.util.function.Supplier;

public class SpineProducers {

    @Produces @Spine
    Supplier<String> text(IssueConfig issueConfig) {
        return issueConfig::getSpineText;
    }

    @Produces @Spine
    Paint paint(Colours colours) {
        return colours.getColor("black");
    }

    @Produces @Spine
    FontFace spineFontFace(
            CloverProperties cloverProperties
    ) {
        return FontFace.of(
                cloverProperties.getFontLocation(),
                62,
                "yellow",
                cloverProperties.getDropShadowXOffset(),
                cloverProperties.getDropShadowYOffset());
    }
}
