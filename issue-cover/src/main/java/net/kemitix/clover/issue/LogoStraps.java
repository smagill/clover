package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.function.Function;

@FrontCover
@ApplicationScoped
public class LogoStraps implements Element<Graphics2D> {

    @Getter private final int priority = 20;

    @Inject CloverProperties cloverProperties;
    @Inject IssueConfig issueConfig;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject IssueDimensions issueDimensions;
    @Inject RightAlignTextEffect<Graphics2D> rightAlignText;

    @Override
    public void draw(Graphics2D drawable) {
        FontFace fontFace = fontFace();
        issueNumber(fontFace)
                .andThen(issueDate(fontFace))
                .andThen(strapLine(fontFace))
                .apply(drawable);
    }

    private Function<Graphics2D, Graphics2D> strapLine(FontFace fontFace) {
        return rightAlignText.fontFace(fontFace)
                .region(issueDimensions.getFrontCrop().toBuilder()
                        .top(10).build().withPadding(85))
                .text("Science Fiction and Fantasy");
    }

    private Function<Graphics2D, Graphics2D> issueDate(FontFace fontFace) {
        int top = 390;
        return rightAlignText.fontFace(fontFace)
                .region(issueDimensions.getFrontCrop().toBuilder()
                        .top(top).build()
                        .withPadding(85))
                .text(issueConfig.getDate());
    }

    private Function<Graphics2D, Graphics2D> issueNumber(FontFace fontFace) {
        int top = 475;
        int left = 85;
        return simpleTextEffect.fontFace(fontFace)
                .region(Region.builder()
                        .top(top).left(left + issueDimensions.getFrontCrop().getLeft())
                        .width(issueDimensions.getFrontCrop().getWidth() -
                                (left + issueDimensions.getFrontCrop().getLeft()))
                        .height(issueDimensions.getFrontCrop().getHeight() - top)
                        .build())
                .text(String.format("Issue %s", issueConfig.getIssue()));
    }

    private FontFace fontFace() {
        return FontFace.of(
                cloverProperties.getFontFile(),
                48,
                issueConfig.getSubTitleColour(),
                XY.at(
                        cloverProperties.getDropShadowXOffset(),
                        cloverProperties.getDropShadowYOffset()));
    }
}
