package net.kemitix.cossmass.clover;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import java.util.Arrays;
import java.util.List;

@Dependent
public class CloverConfig {

    @ConfigProperty(name = "image-types")
    String imageTypes;
    @ConfigProperty(name = "issue-dir")
    String issueDir;
    @ConfigProperty(name = "width")
    int width;
    @ConfigProperty(name = "height")
    int height;
    @ConfigProperty(name = "inches-to-px")
    int inchesToPX;
    @ConfigProperty(name = "drop-shadow-x-offset")
    int dropShadowXOffset;
    @ConfigProperty(name = "drop-shadow-y-offset")
    int dropShadowYOffset;

    public List<String> getImageTypes() {
        return Arrays.asList(imageTypes.split(","));
    }

}
