package net.kemitix.clover;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Getter;
import lombok.Setter;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.Area;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@ConfigProperties(prefix = "clover")
public class ServiceCloverProperties implements CloverProperties {

    String configFile;
    String imageTypes;
    String issueDir;
    float width;
    float height;
    int dpi;
    int dropShadowXOffset;
    int dropShadowYOffset;
    String fontFile;
    int barcodeTop;
    int barcodeLeft;
    float barcodeWidth;
    float barcodeHeight;
    String barcodeFillColour;

    boolean enablePdf;
    boolean enableWebp;
    boolean enableJpg;

    boolean enableKindle;
    boolean enablePaperback;
    boolean enablePaperbackPreview;

    @Override
    public List<String> getImageTypes() {
        return Arrays.asList(imageTypes.split(","));
    }

    @Override
    public URI getFontLocation() {
        return new File(fontFile).toURI();
    }

    @Override
    public int getBarcodeWidth() {
        return (int) (barcodeWidth * dpi);
    }

    @Override
    public int getBarcodeHeight() {
        return (int) (barcodeHeight * dpi);
    }

    @Override
    @Deprecated
    public Area getKindleFrontArea() {
        return Area.builder()
                .width((int) getWidth())
                .height((int) getHeight()).build();
    }

}
