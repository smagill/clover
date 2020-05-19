package net.kemitix.clover.story.card;

import lombok.Getter;
import net.kemitix.clover.spi.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class StoryCardDimensions {

    @Inject IssueConfig issueConfig;
    @Inject StoryCardProperties properties;

    @Getter private float scaleFromOriginal;
    @Getter private Region sourceRegion;
    @Getter private Region cardRegion;

    private int strips = 9;
    private int stripHeight;

    @PostConstruct
    void init() {
        Region targetSizeRegion = properties.getRegion();
        cardRegion = targetSizeRegion.toBuilder().top(0).left(0).build();
        stripHeight = cardRegion.getHeight() / strips;
        IssueStoryCards storyCardSpec = issueConfig.getStoryCards();
        scaleFromOriginal = targetSizeRegion.getWidth() / storyCardSpec.getWidth();
        sourceRegion = Region.builder()
                .top(storyCardSpec.getTop())
                .left(storyCardSpec.getLeft())
                .width(storyCardSpec.getWidth())
                .height((int) (targetSizeRegion.getHeight() * scaleFromOriginal))
                .build();
    }

    public Region getLogoRegion() {
        Region region = cardRegion;
        int top = 0 * stripHeight;
        int height = 3 * stripHeight;
        return region.toBuilder()
                .top(top)
                .height(height)
                .build()
                .withPadding(properties.getPadding());
    }

    public Region getTitleRegion() {
        Region region = cardRegion;
        int top = 3 * stripHeight;
        int height = 3 * stripHeight;
        return region.toBuilder()
                .top(top)
                .height(height)
                .width(properties.getWidth())
                .build()
                .withPadding(properties.getPadding());
    }

    public Region getAuthorRegion() {
        Region region = cardRegion;
        int top = 6 * stripHeight;
        int height = (int) (0.6 * stripHeight);
        return region.toBuilder()
                .top(top)
                .height(height)
                .width(properties.getWidth())
                .build()
                .withPadding(properties.getPadding());
    }

    public Region getSampleRegion() {
        Region region = cardRegion;
        int top = 7 * stripHeight;
        int height = 2 * stripHeight;
        return region.toBuilder()
                .top(top)
                .height(height)
                .width(properties.getWidth())
                .build()
                .withPadding(properties.getPadding());
    }
}
