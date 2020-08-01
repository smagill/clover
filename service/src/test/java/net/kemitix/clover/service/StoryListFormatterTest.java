package net.kemitix.clover.service;

import net.kemitix.clover.spi.IssueAuthor;
import net.kemitix.clover.spi.IssueStory;
import net.kemitix.clover.spi.StoryListFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class StoryListFormatterTest {

    private final StoryListFormatter formatter =
            new StoryListFormatterImpl();
    private final String header = "Header";
    private final String title1 = "Title 1";
    private final IssueAuthor author1 = author("Author", "1");


    private final String title2 = "Title 2";
    private final IssueAuthor author2 = author("Author", "2");

    @Test
    @DisplayName("Formats empty list")
    public void formatsEmptyList() {
        //given
        final List<ServiceIssueConfig.Story> stories = new ArrayList<>();
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
        final List<IssueStory> stories = Collections.singletonList(
                story(author1, title1, "..."));
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
        final List<IssueStory> stories = Collections.singletonList(
                story(author1, title1 + "\n" + title2, "..."));
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
        final List<IssueStory> stories = Collections.singletonList(
                story(
                        author(
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
        final List<IssueStory> stories = Arrays.asList(
                story(author1, title1, "..."),
                story(author2, title2, "..."));
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

    private IssueAuthor author(String forename, String surname) {
        return new IssueAuthor() {
            @Override
            public String getForename() {
                return forename;
            }

            @Override
            public String getSurname() {
                return surname;
            }

            @Override
            public String toString() {
                return String.format("%s %s", forename, surname);
            }
        };
    }

    private IssueStory story(IssueAuthor author, String title1, String sample) {
        return new IssueStory() {
            @Override
            public IssueAuthor getAuthor() {
                return author;
            }

            @Override
            public String getTitle() {
                return title1;
            }

            @Override
            public String getSample() {
                return sample;
            }
        };
    }

}
