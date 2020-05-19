package net.kemitix.clover.story.card;

import lombok.experimental.Delegate;
import net.kemitix.clover.spi.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.net.URISyntaxException;

@StoryCard
@Named("alice")
@ApplicationScoped
public class AliceFontFace implements FontFace {

    @Delegate
    private FontFace delegate;

    @PostConstruct
    void init() throws URISyntaxException {
        delegate = FontFace.of(
                this.getClass()
                        .getResource("alice/Alice-Regular.ttf")
                        .toURI(),
                10,
                "white",
                XY.at(0,0));
    }

}
