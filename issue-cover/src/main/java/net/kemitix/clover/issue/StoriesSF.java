package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@BackCover
@ApplicationScoped
public class StoriesSF implements Element<Graphics2D> {

    @Getter private final int priority = 10;

    private final int top = 200;
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

    private Region region() {
        return Region.builder()
                .top(top).left(left)
                .build();
    }

    private String text() {
        return String.join("\n",
                storyListFormatter.format(
                        "Science Fiction Stories",
                        issueConfig.getStories().getSf()));
    }

}
