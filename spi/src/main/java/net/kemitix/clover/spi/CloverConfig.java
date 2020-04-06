package net.kemitix.clover.spi;

import java.io.File;
import java.util.List;

public interface CloverConfig {
    List<String> getImageTypes();

    int height();

    int width();

    String getBaseDir();

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
}
