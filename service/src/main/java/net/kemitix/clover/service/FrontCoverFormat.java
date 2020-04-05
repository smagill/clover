package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;
import net.kemitix.clover.spi.images.XY;

import java.util.function.Function;
import java.util.logging.Logger;

public abstract class FrontCoverFormat extends CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    FrontCoverFormat.class.getName());

    private final Issue issue;
    private final CloverConfig config;

    protected FrontCoverFormat(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.config = config;
        this.issue = issue;
    }

    @Override
    protected Function<Image, Image> frontCover() {
        return super.frontCover()
                .andThen(drawTitle())
                .andThen(drawSubTitles())
                .andThen(drawAuthors());
    }

    private Function<Image, Image> drawTitle() {
        final FontFace fontFace =
                FontFace.of(
                        config.getFontFile(),
                        217,
                        issue.getTitleColour(),
                        XY.at(
                                config.getDropShadowXOffset(),
                                config.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing title...");
            // TODO - get the title from Issue, line-split it and use
            //  Framing to center
            return image
                    .withText(
                            "Cossmass",
                            XY.at(60 + frontPageXOffset(), 90),
                            fontFace)
                    .withText(
                            "Infinities",
                            XY.at(130 + frontPageXOffset(), 307),
                            fontFace);
        };
    }

    protected abstract int frontPageXOffset();

    private Function<Image, Image> drawSubTitles() {
        final FontFace fontFace =
                FontFace.of(
                        config.getFontFile(),
                        36,
                        issue.getSubTitleColour(),
                        XY.at(
                                config.getDropShadowXOffset(),
                                config.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing subtitle...");
            return image
                    .withText(String.format("Issue %s", issue.getIssue()),
                            XY.at(60 + frontPageXOffset(), 485), fontFace)
                    .withText(issue.getDate(),
                            //TODO use a right-edge and the text width to find X
                            XY.at(1200 + frontPageXOffset(), 485), fontFace)
                    .withText("Science Fiction and Fantasy",
                            XY.at(760 + frontPageXOffset(), 109), fontFace);
        };
    }

    private Function<Image, Image> drawAuthors() {
        final FontFace fontFace =
                FontFace.of(
                        config.getFontFile(),
                        48,
                        issue.getTextColour(),
                        XY.at(
                                config.getDropShadowXOffset(),
                                config.getDropShadowYOffset()));
        return image -> {
            LOGGER.info("Drawing authors...");
            return image
                    .withText(issue.getAuthors(),
                            XY.at(
                                    issue.getAuthorsXOffset() + frontPageXOffset(),
                                    issue.getAuthorsYOffset()),
                            fontFace);
        };
    }

}
