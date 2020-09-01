package net.kemitix.clover.issue;

import lombok.Getter;
import lombok.extern.java.Log;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.function.Consumer;

@Log
@FrontCover
@ApplicationScoped
public class LogoStraps extends AbstractElement {

    @Getter private final int priority = 20;

    @Inject CloverProperties cloverProperties;
    @Inject IssueConfig issueConfig;
    @Inject SimpleTextEffect<Graphics2D> simpleTextEffect;
    @Inject IssueDimensions issueDimensions;

    @Override
    public void draw(Graphics2D drawable, TypedProperties typedProperties) {
        FontFace fontFace = fontFace();
        issueNumber(fontFace)
                .andThen(issueDate(fontFace))
                .andThen(strapLine(fontFace))
                .accept(drawable);
    }

    private Consumer<Graphics2D> strapLine(FontFace fontFace) {
        log.info("Drawing strap-line");
        return simpleTextEffect.fontFace(fontFace)
                .text("Science Fiction and Fantasy")
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.RIGHT)
                .region(issueDimensions.getFrontCrop()
                        .withTop(10)
                        .withWidth(w -> w - 30)
                        .withPadding(85))
                ;
    }

    private Consumer<Graphics2D> issueDate(FontFace fontFace) {
        log.info("Drawing issue date");
        int top = 390;
        return simpleTextEffect.fontFace(fontFace)
                .text(issueConfig.getDate())
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.RIGHT)
                .region(issueDimensions.getFrontCrop()
                        .withTop(top)
                        .withWidth(w -> w - 30)
                        .withPadding(85))
                ;
    }

    private Consumer<Graphics2D> issueNumber(FontFace fontFace) {
        log.info("Drawing issue number");
        int top = 475;
        int left = 85;
        return simpleTextEffect.fontFace(fontFace)
                .text(String.format("Issue %s", issueConfig.getIssue()))
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.LEFT)
                .region(Region.builder()
                        .top(top)
                        .left(left + issueDimensions.getFrontCrop().getLeft() + 30)
                        .width(issueDimensions.getFrontCrop().getWidth() -
                                (left + issueDimensions.getFrontCrop().getLeft()))
                        .height(issueDimensions.getFrontCrop().getHeight() - top)
                        .build())
                ;
    }

    private FontFace fontFace() {
        return FontFace.of(
                cloverProperties.getFontLocation(),
                48,
                issueConfig.getSubTitleColour(),
                XY.at(
                        cloverProperties.getDropShadowXOffset(),
                        cloverProperties.getDropShadowYOffset()));
    }
}
