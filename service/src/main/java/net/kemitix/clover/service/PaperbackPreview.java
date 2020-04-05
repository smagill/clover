package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;

import java.util.function.Function;
import java.util.logging.Logger;

//@Dependent
public class PaperbackPreview extends Paperback {
    private static final Logger LOGGER =
            Logger.getLogger(
                    Kindle.class.getName());

    protected PaperbackPreview(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService,
            final StoryListFormatter storyListFormatter
    ) {
        super(config, issue, imageService, storyListFormatter);
    }

    @Override
    protected String getName() {
        return super.getName() + "-preview";
    }

    @Override
    protected Function<Image, Image> backCover() {
        return super.backCover()
                .andThen(image -> {
                    //TODO: draw barcode space indicator
                    return image;
                });
    }
}
