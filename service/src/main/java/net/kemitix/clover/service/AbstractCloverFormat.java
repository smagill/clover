package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageFactory;
import net.kemitix.properties.typed.TypedProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class AbstractCloverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    AbstractCloverFormat.class.getName());

    private final CloverProperties config;
    private final Issue issue;
    private final ImageFactory imageFactory;
    private Image cover;

    protected AbstractCloverFormat(
            final CloverProperties config,
            final Issue issue,
            final ImageFactory imageFactory) {
        this.config = config;
        this.issue = issue;
        this.imageFactory = imageFactory;
    }

    @PostConstruct
    public void create() throws IOException {
//        final File coverArtFile =
//                Paths.get(config.getBaseDir(), issue.coverArt())
//                        .toFile();
//        final Area area = Area.of(getWidth(), getHeight());
//        cover = imageService.load(coverArtFile)
//                .scaleToCover(area)
//                .crop(XY.at(getCropXOffset(), getCropYOffset()))
//                .apply(frontCover())
//                .apply(spine())
//                .apply(backCover());
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
        final TypedProperties properties = getImageProperties();
        cover.rescale(writeScale())
                .write(Paths.get(config.getIssueDir()), getName(), properties);
    }

    protected TypedProperties getImageProperties() {
        return TypedProperties.create();
    }

    protected abstract String getName();

    protected abstract float writeScale();
}
