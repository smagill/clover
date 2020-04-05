package net.kemitix.clover.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Issue {

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
    private List<String> authors;
    @JsonbProperty("sf-stories")
    private List<List<String>> sfStories;
    @JsonbProperty("fantasy-stories")
    private List<List<String>> fantasyStories;
    @JsonbProperty("reprint-stories")
    private List<List<String>> reprintStories;
    @JsonbProperty("cover-art")
    private String coverArt;
    @JsonbProperty("publication-title")
    private String publicationTitle;

    String coverArt() {
        return "/cossmass/" + coverArt;
    }
}
