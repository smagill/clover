package net.kemitix.clover.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class StoryListFormatterTest {

    private final StoryListFormatter formatter =
            new StoryListFormatter();
    private final String header = "Header";
    private final String title1 = "Title 1";
    private final IssueConfig.Author author1 =
            new IssueConfig.Author("Author", "1");
    private final String title2 = "Title 2";
    private final IssueConfig.Author author2 =
            new IssueConfig.Author("Author", "2");

    @Test
    @DisplayName("Formats empty list")
    public void formatsEmptyList() {
        //given
        final List<IssueConfig.Story> stories = new ArrayList<>();
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
        final List<IssueConfig.Story> stories = Collections.singletonList(
                new IssueConfig.Story(author1, title1, "..."));
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
        final List<IssueConfig.Story> stories = Collections.singletonList(
                new IssueConfig.Story(author1, title1 + "\n" + title2, "..."));
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
        final List<IssueConfig.Story> stories = Collections.singletonList(
                new IssueConfig.Story(
                        new IssueConfig.Author(
                                author1.authorName(),
                                "\n" + author2.authorName()),
                        title1, "..."));
        //when
        final List<String> lines = formatter.format(header, stories);
        //then
        assertThat(lines).containsExactly(
                header,
                "",
                title1,
                "by " + author1.authorName(),
                author2.authorName());
    }

    @Test
    @DisplayName("Formats multiple stories")
    public void formatsMultipleStories() {
        //given
        final List<IssueConfig.Story> stories = Arrays.asList(
                new IssueConfig.Story(author1, title1, "..."),
                new IssueConfig.Story(author2, title2, "..."));
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
