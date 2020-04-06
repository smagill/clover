package net.kemitix.clover.images;

import net.kemitix.clover.spi.TypedProperties;

import javax.enterprise.context.Dependent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

@Dependent
public class JpegImageWriter
        extends AbstractImageIOImageWriter {

    private static final String FORMAT_NAME = "jpg";
    private static final Logger LOGGER =
            Logger.getLogger(
                    JpegImageWriter.class.getName());

    @Override
    public boolean accepts(final String format) {
        return FORMAT_NAME.equals(format);
    }

    @Override
    public void write(
            final BufferedImage image,
            final File file,
            final TypedProperties properties
    ) {
        super.write(withoutAlphaChannel(image), file, properties);
    }

    @Override
    protected String getFormatName() {
        return FORMAT_NAME;
    }

    private BufferedImage withoutAlphaChannel(final BufferedImage image) {
        final BufferedImage copy =
                new BufferedImage(image.getWidth(), image.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
        copy.createGraphics()
                .drawImage(image, 0, 0, null);
        return copy;
    }


}
