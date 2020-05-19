package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@FrontCover
@ApplicationScoped
public class AuthorsElement implements Element<Graphics2D> {

    @Getter private final int priority = 50;

    @Inject IssueConfig issueConfig;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject @FrontCover FontFace fontFace;
    @Inject IssueDimensions issueDimensions;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        int top = issueConfig.getAuthorsYOffset();
        int left = issueConfig.getAuthorsXOffset() +
                issueDimensions.getFrontCrop().getLeft();
        simpleTextEffect.fontFace(fontFace)
                .text(String.join("\n", issueConfig.authors()))
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.LEFT)
                .region(region(top, left))
                .accept(drawable);
    }

    private Region region(int top, int left) {
        Region frontCrop = issueDimensions.getFrontCrop();
        return Region.builder()
                .top(top).left(left)
                .width(frontCrop.getWidth() - left)
                .height(frontCrop.getHeight() - top)
                .build();
    }

}
