package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;
import net.kemitix.clover.spi.images.Region;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;

@ApplicationScoped
public class FormatWriter {
    private IssueConfig issueConfig;
    private ImageService imageService;
    private Image coverArtImage;
    private CloverProperties cloverProperties;
    private Region frontRegion;
    private Region fullRegion;

    public FormatWriter() {
    }

    @Inject
    public FormatWriter(
            final IssueConfig issueConfig,
            final ImageService imageService,
            final CloverProperties cloverProperties
    ) {
        this.issueConfig = issueConfig;
        this.imageService = imageService;
        this.cloverProperties = cloverProperties;
    }

    /**
     * coverArtFile - the file of the cover art image
     * coverArtImage - CALCULATED - the image of the cover art loaded from coverArtFile
     * coverArtArea - CALCULATED - the width and height of coverArtImage
     * frontXY - position within cover art image for front cover
     * frontWidth - width within cover art image for front cover
     * kindleFrontArea - required area for Kindle front cover
     * coverArtScale - CALCULATED - scale up or down factor from frontWidth and kindleFrontArea.width
     * frontArea - CALCULATED - crop area within cover art image for front cover from kindleFrontArea and coverArtScale
     * ASSERT frontXY and frontArea fit within coverArtImage bounds
     * spineInches - width of required spine in inches
     * spineWidth - CALCULATED - width of required spine within cover art images from spineInches, dpi, and coverArtScale
     * fullXY - CALCULATED - position within cover art image for full cover (back + spine + front)
     * - frontXY.x - (spinePX + frontArea.width)
     * fullArea - area within cover art image for full cover
     * ASSERT fullXY and fullArea fit within coverArtImage bounds
     */
    @PostConstruct
    void init() throws IOException {
        //coverArtImage = imageService.load(new File(issueConfig.getCoverArt()));
//        final Area coverArtImageArea = coverArtImage.getArea();
//        final Region coverArtRegion = Region.builder()
//                .width(coverArtImageArea.getWidth())
//                .height(coverArtImageArea.getHeight()).build();
//        final int frontWidth = issueConfig.getFrontWidth();
//        final Area kindleFrontArea = cloverProperties.getKindleFrontArea();
//        final int kindleFrontWidth = kindleFrontArea.getWidth();
//        final float coverArtScale = frontWidth / kindleFrontWidth;
//        final int kindleFrontHeight = kindleFrontArea.getHeight();
//        final int frontHeight = (int) (kindleFrontHeight * coverArtScale);
//        frontRegion = Region.builder()
//                .top(issueConfig.getKindleXOffset())
//                .left(issueConfig.getKindleYOffset())
//                .width(frontWidth)
//                .height(frontHeight).build();
//        assertFits(frontRegion, coverArtRegion);
//        final float spineInches = issueConfig.getSpine();
//        final int dpi = cloverProperties.getDpi();
//        final int spineWidth = (int) (spineInches * dpi * coverArtScale);
//        fullRegion = Region.builder()
//                .top(frontRegion.getLeft() - spineWidth)
//                .left(frontRegion.getTop())
//                .width((frontWidth * 2) + spineWidth)
//                .height(frontRegion.getHeight()).build();
//        assertFits(fullRegion, coverArtRegion);
    }

//    private void assertFits(final Region inner, final Region outer) {
//        assert outer.contains(inner);
//    }

    public void write(final CloverFormat format) {
        final Image cropped = format.getImage();
        cropped.write(Paths.get(cloverProperties.getIssueDir()),
                format.getName(),
                format.getImageProperties());
    }
}
