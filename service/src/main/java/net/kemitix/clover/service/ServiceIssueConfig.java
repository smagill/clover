package net.kemitix.clover.service;

import lombok.Getter;
import lombok.Setter;
import net.kemitix.clover.spi.*;

import javax.enterprise.inject.Vetoed;
import javax.json.bind.annotation.JsonbProperty;
import java.util.*;

@Vetoed
@Setter
@Getter
public class ServiceIssueConfig implements IssueConfig {

    public ServiceIssueConfig() {
    }

    private String issue;
    private String date;
    @JsonbProperty("title-colour")
    private String titleColour;
    @JsonbProperty("sub-title-colour")
    private String subTitleColour;
    @JsonbProperty("text-colour")
    private String textColour;
    private float spine;
    @JsonbProperty("paperback-x-offset")
    private int paperbackXOffset;
    @JsonbProperty("paperback-y-offset")
    private int paperbackYOffset;
    @JsonbProperty("kindle-x-offset")
    private int kindleXOffset;
    @JsonbProperty("kindle-y-offset")
    private int kindleYOffset;
    @JsonbProperty("authors-x-offset")
    private int authorsXOffset;
    @JsonbProperty("authors-y-offset")
    private int authorsYOffset;
    private Cards cards;
    private Stories stories;
    @JsonbProperty("cover-art")
    private String coverArt;
    @JsonbProperty("publication-title")
    private String publicationTitle;
    @JsonbProperty("front-width")
    private int frontWidth;
    @JsonbProperty("story-cards")
    private StoryCards storyCards;

    @Setter
    @Getter
    public static class Stories implements IssueStories {
        private List<Story> sf;
        private List<Story> fantasy;
        private List<Story> reprint;
    }

    @Setter
    @Getter
    public static class Story implements IssueStory {
        private Author author;
        private String title;
        private String sample;
    }

    @Setter
    @Getter
    public static class Author implements IssueAuthor {
        private String surname;
        private String forename;

    }

    @Setter
    @Getter
    public static class Cards implements IssueCards {
        private Crop crop;
    }

    @Setter
    @Getter
    public static class Crop implements IssueCrop {
        private int top;
        private int left;
        private int width;
        private int height;
    }

    @Setter
    @Getter
    public static class StoryCards implements IssueStoryCards {
        int top;
        int left;
        int width;
        int titleFontSize;
    }
}
