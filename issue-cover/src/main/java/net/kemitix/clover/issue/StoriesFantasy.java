package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.fontface.FontFace;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@BackCover
@ApplicationScoped
public class StoriesFantasy extends AbstractElement {

    @Getter private final int priority = 10;

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

    private Region region() {
        return Region.builder()
                .top(issueConfig.getFantasyTop())
                .left(issueConfig.getFantasyLeft())
                .build();
    }

    private String text() {
        return String.join("\n",
                storyListFormatter.format(
                        "Original Fantasy",
                        issueConfig.getStories().getFantasy()));
    }

}
