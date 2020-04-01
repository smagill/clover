package net.kemitix.cossmass.clover;

import net.kemitix.cossmass.clover.images.CloverConfig;
import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.ImageService;

import java.util.function.Function;
import java.util.logging.Logger;

//@Dependent
public class Paperback extends FrontCoverFormat {

    private static final Logger LOGGER =
            Logger.getLogger(
                    Paperback.class.getName());
    private final Issue issue;
    private final CloverConfig config;

    protected Paperback(
            final CloverConfig config,
            final Issue issue,
            final ImageService imageService
    ) {
        super(config, issue, imageService);
        this.config = config;
        this.issue = issue;
    }

    @Override
    protected int frontPageXOffset() {
        return (int) ((config.width() + issue.getSpine()) / 2);
    }

    @Override
    protected int getCropYOffset() {
        return issue.getPaperbackYOffset();
    }

    @Override
    protected int getCropXOffset() {
        return issue.getPaperbackXOffset();
    }

    @Override
    protected String getName() {
        return "paperback";
    }

    @Override
    protected Function<Image, Image> backCover() {
        //TODO draw back cover
        return super.backCover();
    }

    @Override
    protected Function<Image, Image> spine() {
        //TODO draw spine
        return super.spine();
    }
}
