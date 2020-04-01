package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;
import net.kemitix.cossmass.clover.images.XY;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
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
    public void create() throws IOException {
        final File coverArtFile =
                Paths.get(config.getBaseDir(), issue.coverArt())
                        .toFile();
        final Area area = Area.of(getWidth(), getHeight());
        cover = imageService.load(coverArtFile)
                .scaleToCover(area)
                .crop(XY.at(getCropXOffset(), getCropYOffset()), area)
                .apply(frontCover())
                .apply(spine())
                .apply(backCover());
    }

    protected abstract int getHeight();

    protected abstract int getWidth();

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
        LOGGER.info("Drawing the Front Cover");
        return image -> {
            // Do nothing - subclasses should override if they want a spine
            return image;
        };
    }

    protected abstract int getCropYOffset();

    protected abstract int getCropXOffset();

    public void write() {
        cover.rescale(writeScale())
                .write(Paths.get(config.getIssueDir()), getName());
    }

    protected abstract String getName();

    protected abstract float writeScale();
}
