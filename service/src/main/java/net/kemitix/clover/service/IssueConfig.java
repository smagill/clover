package net.kemitix.clover.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kemitix.clover.spi.images.Region;
import net.kemitix.clover.spi.images.XY;

import javax.enterprise.inject.Vetoed;
import javax.json.bind.annotation.JsonbProperty;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Vetoed
@Setter
@Getter
public class IssueConfig {
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
    List<String> authors() {
        return stories.stream()
                .map(Story::getAuthor)
                .sorted(Comparator.comparing(Author::getSurname)
                        .thenComparing(Author::getForename))
                .map(Author::authorName)
                .collect(Collectors.toList());
    }
    @JsonbProperty("cover-art")
    private String coverArt;
    @JsonbProperty("publication-title")
    private String publicationTitle;
    @JsonbProperty("front-width")
    private int frontWidth;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stories {
        private List<Story> sf;
        private List<Story> fantasy;
        private List<Story> reprint;
        public Stream<Story> stream() {
            return Stream.of(sf, fantasy, reprint)
                    .flatMap(Collection::stream);
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Story {
        private Author author;
        private String title;
        private String sample;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private String surname;
        private String forename;
        public String authorName() {
            return String.join(" ", Arrays.asList(forename, surname));
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cards {
        private Crop crop;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Crop {
        private int top;
        private int left;
        private int width;
        private int height;
        public Region getRegion() {
            return Region.builder()
                    .top(top).left(left)
                    .width(width).height(height)
                    .build();
        }
    }
}
