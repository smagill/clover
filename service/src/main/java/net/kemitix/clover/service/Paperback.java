package net.kemitix.clover.service;

import lombok.Getter;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.PdfHeight;
import net.kemitix.clover.spi.PdfWidth;
import net.kemitix.clover.spi.images.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;
import java.util.logging.Logger;

@ApplicationScoped
public class Paperback implements CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Paperback.class.getName());

    private CloverProperties cloverProperties;
    private IssueConfig issueConfig;
    private Dimensions dimensions;
    private Image coverArtImage;
    private StoryListFormatter storyListFormatter;
    @Getter
    private Image image;

    public Paperback() {
    }

    @Inject
    protected Paperback(
            final CloverProperties cloverProperties,
            final IssueConfig issueConfig,
            final Dimensions dimensions,
            final Image coverArtImage,
            final StoryListFormatter storyListFormatter) {
        this.cloverProperties = cloverProperties;
        this.issueConfig = issueConfig;
        this.dimensions = dimensions;
        this.coverArtImage = coverArtImage;
        this.storyListFormatter = storyListFormatter;
    }

    @PostConstruct
    public void init() {
        image = rescale(dimensions.getScaleFromOriginal())
                .andThen(crop(dimensions.getWrapCrop()))
                .andThen(frontCover())
                .andThen(spine())
                .andThen(backCover())
                .apply(coverArtImage);
    }

    private Function<Image, Image> crop(Region cropRegion) {
        return image -> image.crop(cropRegion);
    }

    private Function<Image, Image> rescale(float factor) {
        return image -> image.rescale(factor);
    }

    @Override
    public String getName() {
        return "paperback";
    }

    protected Function<Image, Image> backCover() {
        final FontFace fontFace =
                FontFace.of(
                        cloverProperties.getFontFile(),
                        48,
                        issueConfig.getTextColour(),
                        XY.at(
                                cloverProperties.getDropShadowXOffset(),
                                cloverProperties.getDropShadowYOffset()));
        return drawSFStories(fontFace)
                .andThen(drawFantasyStories(fontFace))
                .andThen(drawReprintStories(fontFace));
    }

    private Function<Image, Image> drawSFStories(final FontFace fontFace) {
        return image -> {
            LOGGER.info("Drawing SF Stories...");
            return image
                    .withText(
                            storyListFormatter.format(
                                    "Science Fiction Stories",
                                    issueConfig.getSfStories()),
                            XY.at(150, 200),
                            fontFace);
        };
    }

    private Function<Image, Image> drawFantasyStories(final FontFace fontFace) {
        return image -> {
            LOGGER.info("Drawing Fantasy Stories...");
            return image
                    .withText(
                            storyListFormatter.format(
                                    "Fantasy Stories",
                                    issueConfig.getFantasyStories()),
                            XY.at(500, 1100),
                            fontFace);
        };
    }

    private Function<Image, Image> drawReprintStories(final FontFace fontFace) {
        return image -> {
            LOGGER.info("Drawing Reprint Stories...");
            return image
                    .withText(
                            storyListFormatter.format(
                                    "Plus",
                                    issueConfig.getReprintStories()),
                            XY.at(150, 1800),
                            fontFace);
        };
    }

    protected Function<Image, Image> spine() {
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
        return image -> image.withFilledArea(
                dimensions.getSpineCrop(),
                "black"
        ).withRotatedCenteredText(
                spineText,
                dimensions.getSpineCrop(),
                fontFace);
    }

    @Override
    public TypedProperties getImageProperties() {
        Region wrapCrop = dimensions.getWrapCrop();
        return TypedProperties.create()
                .with(PdfWidth.class, (int) wrapCrop.getWidth())
                .with(PdfHeight.class, (int) wrapCrop.getHeight());
    }

    protected Function<Image, Image> frontCover() {
        return drawTitle()
                .andThen(drawSubTitles())
                .andThen(drawAuthors())
                ;
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
            // TODO - get the title from Issue, line-split it and use
            //  Framing to center
            return image
                    .withText(
                            "Cossmass",
                            XY.at(60 + frontLeftEdge(), 90),
                            fontFace)
                    .withText(
                            "Infinities",
                            XY.at(130 + frontLeftEdge(), 307),
                            fontFace);
        };
    }

    private int frontLeftEdge() {
        return dimensions.getFrontCrop().getLeft();
    }

    private Function<Image, Image> drawSubTitles() {
        final FontFace fontFace =
                FontFace.of(
                        cloverProperties.getFontFile(),
                        36,
                        issueConfig.getSubTitleColour(),
                        XY.at(
                                cloverProperties.getDropShadowXOffset(),
                                cloverProperties.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing subtitle...");
            return image
                    .withText(String.format("Issue %s", issueConfig.getIssue()),
                            XY.at(60 + frontLeftEdge(), 485), fontFace)
                    .withText(issueConfig.getDate(),
                            //TODO use a right-edge and the text width to find X
                            XY.at(1200 + frontLeftEdge(), 485), fontFace)
                    .withText("Science Fiction and Fantasy",
                            XY.at(760 + frontLeftEdge(), 109), fontFace);
        };
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
