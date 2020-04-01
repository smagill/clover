package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.*;

import javax.enterprise.context.Dependent;
import java.util.function.Function;
import java.util.logging.Logger;

@Dependent
public class Paperback extends FrontCoverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Paperback.class.getName());
    private final Issue issue;
    private final CloverConfig config;
    private final StoryListFormatter storyListFormatter;

    protected Paperback(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService,
            final StoryListFormatter storyListFormatter
    ) {
        super(config, issue, imageService);
        this.config = config;
        this.issue = issue;
        this.storyListFormatter = storyListFormatter;
    }

    @Override
    protected int frontPageXOffset() {
        return config.width() + getSpine();
    }

    private int getSpine() {
        return (int) (issue.getSpine() * config.getInchesToPX());
    }

    @Override
    protected int getCropYOffset() {
        return issue.getPaperbackYOffset();
    }

    @Override
    protected int getCropXOffset() {
        return issue.getPaperbackXOffset();
    }

    @Override
    protected String getName() {
        return "paperback";
    }

    @Override
    protected float writeScale() {
        return 119f / 512f;
    }

    @Override
    protected int getHeight() {
        return config.height();
    }

    @Override
    protected int getWidth() {
        return (int) ((config.width() * 2) + getSpine());
    }

    @Override
    protected Function<Image, Image> backCover() {
        final FontFace fontFace =
                FontFace.of(
                        config.getFontFile(),
                        48,
                        issue.getTextColour(),
                        XY.at(
                                config.getDropShadowXOffset(),
                                config.getDropShadowYOffset()));
        return super.backCover()
                .andThen(drawSFStories(fontFace))
                .andThen(drawFantasyStories(fontFace))
                .andThen(drawReprintStories(fontFace))
                ;
    }

    private Function<Image, Image> drawSFStories(final FontFace fontFace) {
        return image -> {
            LOGGER.info("Drawing SF Stories...");
            return image
                    .withText(
                            storyListFormatter.format(
                                    "Science Fiction Stories",
                                    issue.getSfStories()),
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
                                    issue.getFantasyStories()),
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
                                    issue.getReprintStories()),
                            XY.at(150, 1800),
                            fontFace);
        };
    }

    @Override
    protected Function<Image, Image> spine() {
        final FontFace fontFace =
                FontFace.of(
                        config.getFontFile(),
                        62,
                        "yellow",
                        XY.at(
                                config.getDropShadowXOffset(),
                                config.getDropShadowYOffset()));
        final String spineText = String.format(
                "Cossmass Infinities - Issue %s - %s",
                issue.getIssue(),
                issue.getDate());
        return super.spine()
                .andThen(image ->
                        image.withFilledArea(
                                XY.at(config.width(), 0),
                                Area.of(getSpine(), getHeight()),
                                "black"
                        ))
                .andThen(image ->
                        image.withRotatedCenteredText(
                                spineText,
                                XY.at(config.width(), 0),
                                Area.of(getSpine(), getHeight()),
                                fontFace));
    }
}
