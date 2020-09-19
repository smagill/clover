package net.kemitix.clover.issue;

import lombok.Getter;
import lombok.extern.java.Log;
import net.kemitix.clover.spi.*;
import net.kemitix.fontface.FontFace;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;

@Log
@FrontCover
@ApplicationScoped
public class AuthorsElement extends AbstractElement {

    @Getter private final int priority = 50;

    @Inject IssueConfig issueConfig;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject @FrontCover FontFace fontFace;
    @Inject IssueDimensions issueDimensions;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        log.info("Drawing author list");
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
