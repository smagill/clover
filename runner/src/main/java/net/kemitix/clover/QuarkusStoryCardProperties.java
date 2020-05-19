package net.kemitix.clover;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Getter;
import lombok.Setter;
import net.kemitix.clover.spi.Region;
import net.kemitix.clover.story.card.StoryCardProperties;

@Setter
@Getter
@ConfigProperties(prefix = "clover.story-card")
public class QuarkusStoryCardProperties implements StoryCardProperties {

    private boolean enabled;
    private int width;
    private int height;
    private int padding;
    private int logoFontSize;

    @Override
    public Region getRegion() {
        return Region.builder()
                .width(width).height(height)
                .build();
    }
}
