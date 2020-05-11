package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.XY;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;

@ApplicationScoped
public class Spine implements Function<Image, Image> {
    private CloverProperties cloverProperties;
    private IssueConfig issueConfig;
    private Dimensions dimensions;

    public Spine() {
    }

    @Inject
    public Spine(
            CloverProperties cloverProperties,
            IssueConfig issueConfig,
            Dimensions dimensions
    ) {
        this.cloverProperties = cloverProperties;
        this.issueConfig = issueConfig;
        this.dimensions = dimensions;
    }

    @Override
    public Image apply(Image image) {
        final FontFace fontFace =
                FontFace.of(
                        cloverProperties.getFontFile(),
                        62,
                        "yellow",
                        XY.at(
                                cloverProperties.getDropShadowXOffset(),
                                cloverProperties.getDropShadowYOffset()));
        final String spineText = String.format(
                "%s - Issue %s - %s",
                issueConfig.getPublicationTitle(),
                issueConfig.getIssue(),
                issueConfig.getDate());
        return image.withFilledArea(
                dimensions.getSpineCrop(),
                "black"
        ).withRotatedCenteredText(
                spineText,
                dimensions.getSpineCrop(),
                fontFace);
    }
}
