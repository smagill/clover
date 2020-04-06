package net.kemitix.clover.service;

import lombok.Getter;
import net.kemitix.clover.spi.CloverConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Dependent
@Getter
public class CloverConfigProperties implements CloverConfig {

    @ConfigProperty(name = "base-dir")
    public String baseDir;
    @ConfigProperty(name = "config-file")
    String configFile;
    @ConfigProperty(name = "image-types")
    String imageTypes;
    @ConfigProperty(name = "issue-dir")
    String issueDir;
    @ConfigProperty(name = "width")
    float width;
    @ConfigProperty(name = "height")
    float height;
    @ConfigProperty(name = "dpi")
    int dpi;
    @ConfigProperty(name = "drop-shadow-x-offset")
    int dropShadowXOffset;
    @ConfigProperty(name = "drop-shadow-y-offset")
    int dropShadowYOffset;
    @ConfigProperty(name = "font-file")
    String fontFile;
    @ConfigProperty(name = "barcode-top")
    int barcodeTop;
    @ConfigProperty(name = "barcode-left")
    int barcodeLeft;
    @ConfigProperty(name = "barcode-width")
    float barcodeWidth;
    @ConfigProperty(name = "barcode-height")
    float barcodeHeight;
    @ConfigProperty(name = "barcode-fill-colour")
    String barcodeFillColour;

    @Override
    public List<String> getImageTypes() {
        return Arrays.asList(imageTypes.split(","));
    }

    @Override
    public int height() {
        return (int) (height * dpi);
    }

    @Override
    public int width() {
        return (int) (width * dpi);
    }

    @Override
    public File getFontFile() {
        return new File(fontFile);
    }

    @Override
    public int getBarcodeWidth() {
        return (int) (barcodeWidth * dpi);
    }

    @Override
    public int getBarcodeHeight() {
        return (int) (barcodeHeight * dpi);
    }
}
