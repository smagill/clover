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
            final String title,
            final List<List<String>> stories
    ) {
        final List<String> list = new ArrayList<>();
        list.add(title);
        list.addAll(stories.stream()
                .flatMap(story -> Stream.of(
                        "",
                        story.get(0),
                        "by " + story.get(1)))
                .flatMap(this::splitOnLineBreaks)
                .collect(Collectors.toList()));
        return list;
    }

    private Stream<String> splitOnLineBreaks(final String line) {
        return Arrays.stream(line.split("\n"));
    }
}
