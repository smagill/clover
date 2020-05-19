package net.kemitix.clover.story.card;

import net.kemitix.clover.spi.CloverFormat;
import net.kemitix.clover.spi.Image;
import net.kemitix.clover.spi.IssueConfig;
import net.kemitix.clover.spi.IssueStory;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StoryCardFormat implements CloverFormat {

    @Inject StoryCardProperties properties;
    @Inject IssueConfig issueConfig;
    @Inject StoryCardFactory storyCardFactory;

    private List<Image> images;

    @Override
    public List<Image> getImages() {
        if (images == null) {
            images = buildImages();
        }
        return images;
    }

    private List<Image> buildImages() {
        return issueConfig.getStories().stream()
                .map(storyCardFactory::create)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return "story-card";
    }

    @Override
    public TypedProperties getImageProperties() {
        return TypedProperties.create();
    }

    @Override
    public boolean isEnabled() {
        return properties.isEnabled();
    }
}
