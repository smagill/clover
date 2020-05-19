package net.kemitix.clover.story.card;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;

@StoryCard
@ApplicationScoped
public class SampleBlock implements Element<Graphics2D> {

    @Getter private final int priority = 40;

    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject OpaqueFill<Graphics2D> opaqueFill;
    @Inject CloverProperties cloverProperties;
    @Inject IssueConfig issueConfig;
    @Inject StoryCardDimensions dimensions;
    @Inject @Named("alice") FontFace aliceFontFace;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        IssueStory story =
                typedProperties.find(TypedKeys.Story.class, IssueStory.class)
                        .orElseThrow();
        FontFace fontFace = aliceFontFace;
        var text = story.getSample();
        Region region = dimensions.getSampleRegion();
        opaqueFill
                .opacity(0.7D)
                .colour("black")
                .region(region)
                .accept(drawable);
        simpleTextEffect
                .fontFace(fontFace.withColour("white"))
                .text(text)
                .vAlign(TextEffect.VAlignment.CENTRE)
                .hAlign(TextEffect.HAlignment.CENTRE)
                .region(region.withPadding(20))
                .accept(drawable);
    }
}
