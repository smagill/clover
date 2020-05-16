package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@BackCover
@ApplicationScoped
public class StoriesReprints implements Element<Graphics2D> {

    @Getter private final int priority = 30;

    private final int top = 1800;
    private final int left = 150;

    @Inject @BackCover FontFace fontFace;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject StoryListFormatter storyListFormatter;
    @Inject IssueConfig issueConfig;

    @Override
    public void draw(Graphics2D drawable) {
        simpleTextEffect.fontFace(fontFace)
                .region(region())
                .text(text())
                .apply(drawable);
    }

    private String text() {
        return String.join("\n",
                storyListFormatter.format(
                        "Plus",
                        issueConfig.getStories().getReprint()));
    }

    private Region region() {
        return Region.builder()
                .top(top).left(left)
                .build();
    }

}
