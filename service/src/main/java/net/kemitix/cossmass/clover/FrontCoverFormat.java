package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;

import java.util.function.Function;

public abstract class FrontCoverFormat extends CloverFormat {
    protected FrontCoverFormat(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
    }

    @Override
    protected Function<Image, Image> frontCover() {
        return super.frontCover();
    }
}
