package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.Image;
import net.kemitix.cossmass.clover.images.*;
import org.beryx.awt.color.ColorFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.awt.Image.SCALE_SMOOTH;

class CloverImage implements Image {

    private static final Logger LOGGER =
            Logger.getLogger(
                    CloverImage.class.getName());

    private final BufferedImage image;
    private final CloverConfig config;
    private final FontCache fontCache;

    CloverImage(
            final BufferedImage image,
            final CloverConfig config,
            final FontCache fontCache
    ) {
        this.image = image;
        this.config = config;
        this.fontCache = fontCache;
    }

    @Override
    public Image scaleToCover(
            final int width,
            final int height
    ) {
        LOGGER.info(String.format("Scaling to cover: %d x %d", width, height));
        final int originalWidth = getWidth();
        final int originalHeight = getHeight();
        final int ratio = originalWidth / originalHeight;
        LOGGER.info("Ratio: " + ratio);
        final int newWidth;
        final int newHeight;
        if (ratio > 1) { // is wide
            newWidth = height * ratio;
            newHeight = height;
        } else { // is tall
            newWidth = width;
            newHeight = width / ratio;
        }
        LOGGER.info(String.format("Resizing to %dx%d",
                newWidth, newHeight));
        return scaleTo(newWidth, newHeight);
    }

    private Image scaleTo(final int width, final int height) {
        final BufferedImage resized =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        resized.createGraphics()
                .drawImage(
                        image.getScaledInstance(width, height, SCALE_SMOOTH),
                        0,
                        0,
                        null);
        return new CloverImage(resized, config, fontCache);
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public Image crop(
            final int xOffset,
            final int yOffset,
            final int width,
            final int height
    ) {
        LOGGER.info(String.format("Cropping from %d x %d by %d x %d",
                xOffset, yOffset,
                width, height
        ));
        final BufferedImage cropped =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        cropped.createGraphics()
                .drawImage(
                        image.getSubimage(xOffset, yOffset, width, height),
                        0,
                        0,
                        null);
        return new CloverImage(cropped, config, fontCache);
    }

    @Override
    public void write(
            final Path path,
            final String name
    ) {
        LOGGER.info(String.format("Writing %s to %s", name, path));
        config.getImageTypes()
                .forEach(format -> {
                    final File file =
                            path.resolve(name + "." + format)
                                    .toFile();
                    write(format, file);
                });
    }

    @Override
    public Image withText(
            final String text,
            final XY xy,
            final FontFace fontFace
    ) {
        LOGGER.info(String.format("Drawing text: %s at %dx%d - %d",
                text,
                xy.getX(),
                xy.getY(),
                fontFace.getSize()));
        final BufferedImage withText = copyImage();
        final Graphics2D graphics = withText.createGraphics();
        final Font font = fontCache.loadFont(fontFace);
        graphics.setFont(font);
        final Rectangle2D stringBounds =
                font.getStringBounds(text, graphics.getFontRenderContext());
        // Drop Shadow
        final XY shadowOffset = fontFace.getShadowOffset();
        if (shadowOffset.getX() != 0 || shadowOffset.getY() != 0) {
            graphics.setPaint(getColor(fontFace.getShadowColour()));
            graphics.drawString(text,
                    xy.getX() + shadowOffset.getX(),
                    (int) (xy.getY() - stringBounds.getY() + shadowOffset.getY()));
        }
        // Text
        graphics.setPaint(getColor(fontFace.getColour()));
        graphics.drawString(text,
                xy.getX(),
                (int) (xy.getY() - stringBounds.getY()));
        return new CloverImage(withText, config, fontCache);
    }

    private Color getColor(final String colour) {
        return Optional.ofNullable(
                ColorFactory.valueOf(colour))
                .orElseThrow(() ->
                        new FatalCloverError(
                                "Unknown colour: " + colour));
    }

    private BufferedImage copyImage() {
        final BufferedImage copy =
                new BufferedImage(getWidth(), getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
        copy.createGraphics()
                .drawImage(image, 0, 0, null);
        return copy;
    }

    private void write(
            final String format,
            final File file
    ) {
        LOGGER.info(String.format("Writing %s file as %s", format, file));
        try {
            if (ImageIO.write(image, format, file)) {
                LOGGER.info("Wrote: " + file);
            } else {
                LOGGER.severe("No writer found for " + format);
            }
        } catch (final IOException e) {
            LOGGER.severe("Failed to write " + file);
        }
    }

}
