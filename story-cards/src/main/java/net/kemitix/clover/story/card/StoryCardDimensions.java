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

    private int rows = 12;
    private int cols = 12;
    private int rowHeight;
    private int colWidth;

    @PostConstruct
    void init() {
        Region targetSizeRegion = properties.getRegion();
        cardRegion = targetSizeRegion.toBuilder().top(0).left(0).build();
        rowHeight = cardRegion.getHeight() / rows;
        colWidth = cardRegion.getWidth() / cols;
        IssueStoryCards storyCardSpec = issueConfig.getStoryCards();
        scaleFromOriginal = targetSizeRegion.getWidth() / storyCardSpec.getWidth();
        sourceRegion = Region.builder()
                .top(storyCardSpec.getTop())
                .left(storyCardSpec.getLeft())
                .width(properties.getWidth())
                .height((int) (targetSizeRegion.getHeight() * scaleFromOriginal))
                .build();
    }

    public Region getLogoRegion() {
        return gridPosition(0, 0, 12, 2);
    }

    public Region getTitleRegion() {
        return gridPosition(3, 0, 7, 6);
    }

    public Region getAuthorRegion() {
        return gridPosition(10, 0, 12, 2);
    }

    private Region gridPosition(
            int top,
            int left,
            int width,
            int height
    ) {
        return cardRegion.toBuilder()
                .top(top * rowHeight)
                .left(left * colWidth)
                .height(height * rowHeight)
                .width(width * colWidth)
                .build()
                .withPadding(properties.getPadding());
    }
}
