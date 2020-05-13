package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.SimpleTextEffect;
import net.kemitix.clover.spi.images.FontFace;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.Region;
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

    @Inject
    CloverProperties cloverProperties;
    @Inject
    IssueConfig issueConfig;
    @Inject
    StoryListFormatter storyListFormatter;
    @Inject
    SimpleTextEffect simpleTextEffect;

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
            int top = 200;
            int left = 150;
            return simpleTextEffect.fontFace(fontFace)
                    .region(Region.builder()
                            .top(top).left(left)
                            .width(image.getRegion().getWidth() - left)
                            .height(image.getRegion().getHeight() - top)
                            .build())
                    .text(String.join("\n",
                            storyListFormatter.format(
                                    "Science Fiction Stories",
                                    issueConfig.getStories().getSf())))
                    .apply(image);
        };
    }

    private Function<Image, Image> drawFantasyStories(final FontFace fontFace) {
        return image -> {
            LOGGER.info("Drawing Fantasy Stories...");
            int top = 1100;
            int left = 500;
            return simpleTextEffect.fontFace(fontFace)
                    .region(Region.builder()
                            .top(top).left(left)
                            .width(image.getRegion().getWidth() - left)
                            .height(image.getRegion().getHeight() - top)
                            .build())
                    .text(String.join("\n",
                            storyListFormatter.format(
                                    "Fantasy Stories",
                                    issueConfig.getStories().getFantasy())))
                    .apply(image);
        };
    }

    private Function<Image, Image> drawReprintStories(final FontFace fontFace) {
        return image -> {
            LOGGER.info("Drawing Reprint Stories...");
            int top = 1800;
            int left = 150;
            return simpleTextEffect.fontFace(fontFace)
                    .region(Region.builder()
                            .top(top).left(left)
                            .width(image.getRegion().getWidth() - left)
                            .height(image.getRegion().getHeight() - top)
                            .build())
                    .text(String.join("\n",
                            storyListFormatter.format(
                                    "Plus",
                                    issueConfig.getStories().getReprint())))
                    .apply(image);
        };
    }

}
