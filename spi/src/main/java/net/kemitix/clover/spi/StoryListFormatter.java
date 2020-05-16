package net.kemitix.clover.spi;

import java.util.List;

public interface StoryListFormatter {
    List<String> format(
            String label,
            List<? extends IssueStory> stories
    );
}
