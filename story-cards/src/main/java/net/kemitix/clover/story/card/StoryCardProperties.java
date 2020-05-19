package net.kemitix.clover.story.card;

import net.kemitix.clover.spi.Region;

public interface StoryCardProperties {
    boolean isEnabled();

    int getWidth();

    int getHeight();

    Region getRegion();

    int getPadding();
    int getLogoFontSize();
}
