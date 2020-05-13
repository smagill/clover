package net.kemitix.clover;

import lombok.Getter;
import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.Area;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Getter
public class ServiceCloverProperties implements CloverProperties {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ServiceCloverProperties.class.getName());

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

    @PostConstruct
    public void init() {
        LOGGER.info("Config file: " + Paths.get(issueDir, configFile));
        LOGGER.info("Font file  : " + fontFile);
    }

    @Override
    public List<String> getImageTypes() {
        return Arrays.asList(imageTypes.split(","));
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

    @Override
    @Deprecated
    public Area getKindleFrontArea() {
        return Area.builder()
                .width((int) getWidth())
                .height((int) getHeight()).build();
    }
}
