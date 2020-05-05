package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.clover.spi.PdfHeight;
import net.kemitix.clover.spi.PdfWidth;
import net.kemitix.clover.spi.images.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.function.Function;
import java.util.logging.Logger;

@ApplicationScoped
public class Paperback extends FrontCoverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Paperback.class.getName());
    private Issue issue;
    private CloverConfig config;
    private ImageService imageService;
    private StoryListFormatter storyListFormatter;

    public Paperback() {
    }

    @Inject
    protected Paperback(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService,
            final StoryListFormatter storyListFormatter
    ) {
        this.config = config;
        this.issue = issue;
        this.imageService = imageService;
        this.storyListFormatter = storyListFormatter;
    }

    @Override
    protected int frontPageXOffset() {
        return config.width() + getSpine();
    }

    private int getSpine() {
        return (int) (issue.getSpine() * config.getDpi());
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
        return 1;
    }

    @Override
    protected CloverConfig getCloverConfig() {
        return config;
    }

    @Override
    protected Issue getIssue() {
        return issue;
    }

    @Override
    protected ImageService getImageService() {
        return imageService;
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
                "%s - Issue %s - %s",
                issue.getPublicationTitle(),
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

    @Override
    protected TypedProperties getImageProperties() {
        final int width = config.width();
        final int height = config.height();
        final float spine = issue.getSpine() * config.getDpi();
        final int pdfWidth = (int) ((width * 2) + spine);
        final float scale = 119f / 512;
        final int scaledWidth = (int) (pdfWidth * scale);
        final int scaledHeight = (int) (height * scale);
        return super.getImageProperties()
                .with(PdfWidth.class, scaledWidth)
                .with(PdfHeight.class, scaledHeight);
    }
}
