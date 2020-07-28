package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@BackCover
@ApplicationScoped
public class StoriesReprints implements Element<Graphics2D> {

    @Getter private final int priority = 30;

    @Inject @BackCover FontFace fontFace;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject StoryListFormatter storyListFormatter;
    @Inject IssueConfig issueConfig;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        simpleTextEffect.fontFace(fontFace)
                .text(text())
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.LEFT)
                .region(region())
                .accept(drawable);
    }

    private String text() {
        return String.join("\n",
                storyListFormatter.format(
                        "Plus",
                        issueConfig.getStories().getReprint()));
    }

    private Region region() {
        return Region.builder()
                .top(issueConfig.getReprintTop())
                .left(issueConfig.getReprintLeft())
                .build();
    }

}
