package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.function.Consumer;

@FrontCover
@ApplicationScoped
public class LogoStraps implements Element<Graphics2D> {

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
        return simpleTextEffect.fontFace(fontFace)
                .text("Science Fiction and Fantasy")
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.RIGHT)
                .region(issueDimensions.getFrontCrop().toBuilder()
                        .top(10).build().withPadding(85))
                ;
    }

    private Consumer<Graphics2D> issueDate(FontFace fontFace) {
        int top = 390;
        return simpleTextEffect.fontFace(fontFace)
                .text(issueConfig.getDate())
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.RIGHT)
                .region(issueDimensions.getFrontCrop().toBuilder()
                        .top(top).build()
                        .withPadding(85))
                ;
    }

    private Consumer<Graphics2D> issueNumber(FontFace fontFace) {
        int top = 475;
        int left = 85;
        return simpleTextEffect.fontFace(fontFace)
                .text(String.format("Issue %s", issueConfig.getIssue()))
                .vAlign(TextEffect.VAlignment.TOP)
                .hAlign(TextEffect.HAlignment.LEFT)
                .region(Region.builder()
                        .top(top).left(left + issueDimensions.getFrontCrop().getLeft())
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
