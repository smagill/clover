package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class CloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverFormat.class.getName());

    private final CloverConfig config;
    private final Issue issue;
    private final ImageService imageService;
    private Image cover;

    protected CloverFormat(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService) {
        this.config = config;
        this.issue = issue;
        this.imageService = imageService;
    }

    @PostConstruct
    public void create() {
        LOGGER.info("create()");
        final File coverArtFile =
                Paths.get(config.baseDir, issue.coverArt())
                        .toFile();

        cover = imageService.load(coverArtFile)
                .scaleToCover(config.height, config.width)
                .crop(getCropXOffset(), getCropYOffset(), config.width, config.height)
                .apply(frontCover())
                .apply(spine())
                .apply(backCover());
    }

    protected Function<Image, Image> backCover() {
        return image -> {
            LOGGER.info("Drawing the Back Cover");
            // Do nothing - subclasses should override if they want a back cover
            return image;
        };
    }

    protected Function<Image, Image> spine() {
        return image -> {
            LOGGER.info("Drawing the Spine");
            // Do nothing - subclasses should override if they want a spine
            return image;
        };
    }

    protected Function<Image, Image> frontCover() {
        return image -> {
            LOGGER.info("Drawing the Front Cover");
            //TODO draw front cover
            return image;
        };
    }

    protected abstract int getCropYOffset();

    protected abstract int getCropXOffset();

    public void write() {
        LOGGER.info("write()");
    }
}
