package net.kemitix.cossmass.clover;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@Setter
@ToString
@NoArgsConstructor
public class Issue {

    String issue;
    String date;
    @JsonbProperty("title-colour")
    String titleColour;
    @JsonbProperty("sub-title-colour")
    String subTitleColour;
    @JsonbProperty("text-colour")
    String textColour;
    float spine;
    @JsonbProperty("paperback-x-offset")
    int paperbackXOffset;
    @JsonbProperty("paperback-y-offset")
    int paperbackYOffset;
    @JsonbProperty("kindle-x-offset")
    int kindleXOffset;
    @JsonbProperty("kindle-y-offset")
    int kindleYOffset;
    @JsonbProperty("authors-x-offset")
    int authorsXOffset;
    @JsonbProperty("authors-y-offset")
    int authorsYOffset;
    List<String> authors;
    @JsonbProperty("sf-stories")
    List<List<String>> sfStories;
    @JsonbProperty("fantasy-stories")
    List<List<String>> fantasyStories;
    @JsonbProperty("reprint-stories")
    List<List<String>> reprintStories;
    @JsonbProperty("cover-art")
    private String coverArt;

    String coverArt() {
        return "/cossmass/" + coverArt;
    }
}
