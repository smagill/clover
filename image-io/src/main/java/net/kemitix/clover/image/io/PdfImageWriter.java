package net.kemitix.clover.image.io;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.PdfHeight;
import net.kemitix.clover.spi.PdfWidth;
import net.kemitix.clover.spi.ImageWriter;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class PdfImageWriter implements ImageWriter {

    private static final String FORMAT_NAME = "pdf";
    private static final Logger LOGGER =
            Logger.getLogger(
                    PdfImageWriter.class.getName());

    @Inject CloverProperties cloverProperties;

    @Override
    public boolean accepts(final String format) {
        return FORMAT_NAME.equals(format) && cloverProperties.isEnablePdf();
    }

    @Override
    public void write(
            final BufferedImage srcImage,
            final File file,
            final TypedProperties properties
    ) {
        final int pageWidth =
                properties.find(PdfWidth.class, Integer.class)
                        .orElseGet(srcImage::getWidth);
        final int pageHeight =
                properties.find(PdfHeight.class, Integer.class)
                        .orElseGet(srcImage::getHeight);
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(srcImage, "png", baos);
            final ImageData imageData = ImageDataFactory.create(baos.toByteArray());
            final PdfWriter writer = new PdfWriter(file);
            final PdfDocument pdfDocument = new PdfDocument(writer);
            final PageSize pageSize = new PageSize(
                    pageWidth,
                    pageHeight);
            pdfDocument.setDefaultPageSize(pageSize);
            final Document document = new Document(pdfDocument);
            document.setMargins(0, 0, 0, 0);
            final Image image = new Image(imageData);
            image.setFixedPosition(0, 0);
            image.scaleToFit(pageWidth, pageHeight);
            document.add(image);
            document.close();
            LOGGER.info(String.format("Wrote %s", file));
        } catch (final IOException e) {
            LOGGER.severe("Failed to write " + file);
        }
    }
}
