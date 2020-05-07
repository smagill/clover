package net.kemitix.clover.service;

public class Issue {

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
     * spineWidth - CALCULATED - width of required spine within cover art images from spineInches, dpi, frontArea and kindleFrontArea
     * fullXY - CALCULATED - position within cover art image for full cover (back + spine + front)
     *        - frontXY.x - (spinePX + frontArea.width)
     * fullArea - area within cover art image for full cover
     * ASSERT fullXY and fullArea fit within coverArtImage bounds
     */
}
