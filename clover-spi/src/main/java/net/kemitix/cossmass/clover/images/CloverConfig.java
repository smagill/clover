package net.kemitix.cossmass.clover.images;

import java.util.List;

public interface CloverConfig {
    List<String> getImageTypes();

    int height();

    int width();

    String getBaseDir();

    String getIssueDir();

    String getConfigFile();
}
