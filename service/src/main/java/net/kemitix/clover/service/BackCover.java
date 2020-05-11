package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.XY;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;
import java.util.logging.Logger;

@ApplicationScoped
public class BackCover implements Function<Image, Image> {

    private static final Logger LOGGER =
            Logger.getLogger(
                    BackCover.class.getName());

    private CloverProperties cloverProperties;
    private IssueConfig issueConfig;
    private StoryListFormatter storyListFormatter;

    public BackCover() {
    }

    @Inject
    public BackCover(
            CloverProperties cloverProperties,
            IssueConfig issueConfig,
            StoryListFormatter storyListFormatter
    ) {
        this.cloverProperties = cloverProperties;
        this.issueConfig = issueConfig;
        this.storyListFormatter = storyListFormatter;
    }

    @Override
    public Image apply(Image image) {
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
                .andThen(drawReprintStories(fontFace))
                .apply(image);
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

}
