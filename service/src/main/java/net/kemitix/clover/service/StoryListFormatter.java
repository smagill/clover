package net.kemitix.clover.service;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Dependent
public class StoryListFormatter {

    public List<String> format(
            final String label,
            final List<IssueConfig.Story> stories
    ) {
        final List<String> list = new ArrayList<>();
        list.add(label);
        list.addAll(stories.stream()
                .flatMap(story -> Stream.of(
                        "",
                        story.getTitle(),
                        "by " + story.getAuthor().authorName()))
                .flatMap(this::splitOnLineBreaks)
                .collect(Collectors.toList()));
        return list;
    }

    private Stream<String> splitOnLineBreaks(final String line) {
        return Arrays.stream(line.split("\n"));
    }
}
