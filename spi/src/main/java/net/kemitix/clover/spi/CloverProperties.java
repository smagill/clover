package net.kemitix.clover.spi;

import net.kemitix.clover.spi.images.Area;

import java.io.File;
import java.util.List;

public interface CloverProperties {
    List<String> getImageTypes();

    /**
     * Front cover height in inches.
     */
    float getHeight();

    /**
     * Front cover width in inches.
     */
    float getWidth();

    String getIssueDir();

    String getConfigFile();

    File getFontFile();

    int getDropShadowXOffset();

    int getDropShadowYOffset();

    int getDpi();

    int getBarcodeLeft();

    int getBarcodeTop();

    int getBarcodeWidth();

    int getBarcodeHeight();

    String getBarcodeFillColour();

    Area getKindleFrontArea();
}
