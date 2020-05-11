package net.kemitix.clover.service;

import net.kemitix.clover.spi.CenteredTextEffect;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.RightAlignTextEffect;
import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.Region;
import net.kemitix.clover.spi.images.XY;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class FrontCover implements Function<Image, Image> {

    private static final Logger LOGGER =
            Logger.getLogger(
                    FrontCover.class.getName());

    private CloverProperties cloverProperties;
    private IssueConfig issueConfig;
    private Dimensions dimensions;
    private CenteredTextEffect centeredText;
    private RightAlignTextEffect rightAlignText;

    public FrontCover() {
    }

    @Inject
    public FrontCover(
            CloverProperties cloverProperties,
            IssueConfig issueConfig,
            Dimensions dimensions,
            CenteredTextEffect centeredText,
            RightAlignTextEffect rightAlignText
    ) {
        this.cloverProperties = cloverProperties;
        this.issueConfig = issueConfig;
        this.dimensions = dimensions;
        this.centeredText = centeredText;
        this.rightAlignText = rightAlignText;
    }


    @Override
    public Image apply(Image image) {
        return drawTitle()
                .andThen(drawSubTitles())
                .andThen(drawAuthors())
                .apply(image);
    }

    private Function<Image, Image> drawTitle() {
        final FontFace fontFace =
                FontFace.of(
                        cloverProperties.getFontFile(),
                        217,
                        issueConfig.getTitleColour(),
                        XY.at(
                                cloverProperties.getDropShadowXOffset(),
                                cloverProperties.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing title...");
            var text = String.join("\n", issueConfig.getPublicationTitle().split(" "));
            return centeredText
                    .fontFace(fontFace)
                    .region(dimensions.getFrontCrop().withPadding(85))
                    .text(text)
                    .apply(image);
        };
    }

    private int frontLeftEdge() {
        return dimensions.getFrontCrop().getLeft();
    }

    private Function<Image, Image> drawSubTitles() {
        final FontFace fontFace =
                FontFace.of(
                        cloverProperties.getFontFile(),
                        48,
                        issueConfig.getSubTitleColour(),
                        XY.at(
                                cloverProperties.getDropShadowXOffset(),
                                cloverProperties.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing subtitle...");
            return drawDateSubtitle(fontFace)
                    .andThen(drawBannerSubtitle(fontFace))
                    .apply(image)
                    .withText(String.format("Issue %s", issueConfig.getIssue()),
                            XY.at(85 + frontLeftEdge(), 475), fontFace)
                    ;
        };
    }

    private Function<Image, Image> drawBannerSubtitle(FontFace fontFace) {
        return image ->
                rightAlignText.fontFace(fontFace)
                        .region(dimensions.getFrontCrop().toBuilder()
                               .top(10).build().withPadding(85))
                        .text("Science Fiction and Fantasy")
                        .apply(image);
    }

    private Function<Image, Image> drawDateSubtitle(FontFace fontFace) {
        return rightAlignText.fontFace(fontFace)
                .region(Region.builder()
                        .top(390).left(frontLeftEdge())
                        .width(dimensions.getFrontCrop().getWidth())
                        .height(dimensions.getFrontCrop().getHeight())
                        .build().withPadding(85))
                .text(issueConfig.getDate());
    }

    private Function<Image, Image> drawAuthors() {
        final FontFace fontFace =
                FontFace.of(
                        cloverProperties.getFontFile(),
                        48,
                        issueConfig.getTextColour(),
                        XY.at(
                                cloverProperties.getDropShadowXOffset(),
                                cloverProperties.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing authors...");
            return image
                    .withText(issueConfig.getAuthors(),
                            XY.at(
                                    issueConfig.getAuthorsXOffset() + frontLeftEdge(),
                                    issueConfig.getAuthorsYOffset()),
                            fontFace);
        };
    }
}
