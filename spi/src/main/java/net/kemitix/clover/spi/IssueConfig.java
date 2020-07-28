package net.kemitix.clover.spi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface IssueConfig {

    String getIssue();

    String getTitleColour();

    String getSubTitleColour();

    int getKindleYOffset();

    int getKindleXOffset();

    int getFrontWidth();

    float getSpine();

    String getTextColour();

    String getCoverArt();

    String getPublicationTitle();

    String getDate();

    int getAuthorsYOffset();

    int getAuthorsXOffset();

    default List<String> authors() {
        return getStories().stream()
                .map(IssueStory::getAuthor)
                .sorted(Comparator.comparing(IssueAuthor::getSurname)
                        .thenComparing(IssueAuthor::getForename))
                .map(IssueAuthor::authorName)
                .collect(Collectors.toList());
    }

    IssueStories getStories();


    default String getSpineText() {
        return String.format("%s - Issue %s - %s",
                getPublicationTitle(),
                getIssue(),
                getDate());
    }

    IssueStoryCards getStoryCards();

    int getReprintTop();

    int getReprintLeft();

    int getFantasyTop();

    int getFantasyLeft();

    int getSfTop();

    int getSfLeft();
}
