package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.*;

import java.io.File;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class FrontCoverFormat extends CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    FrontCoverFormat.class.getName());

    private final Issue issue;
    private final CloverConfig config;

    protected FrontCoverFormat(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.config = config;
        this.issue = issue;
    }

    @Override
    protected Function<Image, Image> frontCover() {
        return super.frontCover()
                .andThen(drawTitle())
                .andThen(drawSubTitles())
                .andThen(drawAuthors());
    }

    private Function<Image, Image> drawTitle() {
        final File font = config.getFontFile();
        final int size = 217;
        final String colour = issue.getTitleColour();
        final XY shadowOffset = XY.at(
                config.getDropShadowXOffset(),
                config.getDropShadowYOffset());
        return image -> {
            LOGGER.info("Drawing title...");
            return image
                    .withText(
                            "Cossmass",
                            XY.at(60 + frontPageXOffset(), 90),
                            FontFace.of(font, size, colour, shadowOffset))
                    .withText(
                            "Infinities",
                            XY.at(130 + frontPageXOffset(), 307),
                            FontFace.of(font, size, colour, shadowOffset));
        };
    }

    protected abstract int frontPageXOffset();

    private Function<Image, Image> drawSubTitles() {
        return image -> {
            LOGGER.info("Drawing subtitle...");
            return image;
        };
    }

    private Function<Image, Image> drawAuthors() {
        return image -> {
            LOGGER.info("Drawing authors...");
            return image;
        };
    }

}
