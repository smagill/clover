package net.kemitix.cossmass.clover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StoryListFormatterTest {

    private final StoryListFormatter formatter =
            new StoryListFormatter();
    private final String header = "Header";
    private final String title1 = "Title 1";
    private final String author1 = "Author 1";
    private final String title2 = "Title 2";
    private final String author2 = "Author 2";

    @Test
    @DisplayName("Formats empty list")
    public void formatsEmptyList() {
        //given
        final List<List<String>> stories = new ArrayList<>();
        //when
        final List<String> lines = formatter.format(header, stories);
        //then
        assertThat(lines).containsExactly(
                header);
    }

    @Test
    @DisplayName("Formats a single story")
    public void formatsSingleStory() {
        //given
        final List<List<String>> stories = new ArrayList<>();
        final List<String> story = Arrays.asList(
                title1, author1
        );
        stories.add(story);
        //when
        final List<String> lines = formatter.format(header, stories);
        //then
        assertThat(lines).containsExactly(
                header,
                "",
                title1,
                "by " + author1);
    }

    @Test
    @DisplayName("Formats a story with line breaks in title")
    public void formatsStoryWithLineBreaksInTitle() {
        //given
        final List<List<String>> stories = new ArrayList<>();
        final List<String> story = Arrays.asList(
                title1 + "\n" + title2, author1
        );
        stories.add(story);
        //when
        final List<String> lines = formatter.format(header, stories);
        //then
        assertThat(lines).containsExactly(
                header,
                "",
                title1,
                title2,
                "by " + author1);
    }

    @Test
    @DisplayName("Formats a story with line breaks in author")
    public void formatsStoryWithLineBreaksInAuthor() {
        //given
        final List<List<String>> stories = new ArrayList<>();
        final List<String> story = Arrays.asList(
                title1, author1 + "\n" + author2
        );
        stories.add(story);
        //when
        final List<String> lines = formatter.format(header, stories);
        //then
        assertThat(lines).containsExactly(
                header,
                "",
                title1,
                "by " + author1,
                author2);
    }

    @Test
    @DisplayName("Formats multiple stories")
    public void formatsMultipleStories() {
        //given
        final List<List<String>> stories = new ArrayList<>();
        stories.add(Arrays.asList(title1, author1));
        stories.add(Arrays.asList(title2, author2));
        //when
        final List<String> lines = formatter.format(header, stories);
        //then
        assertThat(lines).containsExactly(
                header,
                "",
                title1,
                "by " + author1,
                "",
                title2,
                "by " + author2);
    }
}