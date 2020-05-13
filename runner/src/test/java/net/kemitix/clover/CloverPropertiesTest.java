package net.kemitix.clover;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CloverPropertiesTest
        implements WithAssertions {

    private final ServiceCloverProperties cloverConfig =
            new ServiceCloverProperties();

    @Test
    @DisplayName("ImageTypes are parsed as comma-delimited")
    public void imageTypesParsing() {
        //given
        cloverConfig.imageTypes = "alpha,beta,gamma";
        //when
        final List<String> imageTypes = cloverConfig.getImageTypes();
        //then
        assertThat(imageTypes)
                .containsExactly(
                        "alpha",
                        "beta",
                        "gamma");
    }

    @Test
    @DisplayName("Height has NOT been converted from inches to PX")
    public void heightIsInInches() {
        //given
        final Random random = new Random();
        final int height = random.nextInt();
        cloverConfig.height = height;
        //when
        final float result = cloverConfig.getHeight();
        //then
        assertThat(result)
                .isEqualTo(height);
    }

    @Test
    @DisplayName("Width has NOT been converted from inches to PX")
    public void widthIsInInches() {
        //given
        final Random random = new Random();
        final int width = random.nextInt();
        cloverConfig.width = width;
        //when
        final float result = cloverConfig.getWidth();
        //then
        assertThat(result)
                .isEqualTo(width);
    }

    @Test
    @DisplayName("Get plain values")
    public void getPlainValues() {
        //given
        final String issueDir = UUID.randomUUID().toString();
        final Random random = new Random();
        final int width = random.nextInt();
        final int height = random.nextInt();
        final int dpi = random.nextInt();
        final int dropShadowXOffset = random.nextInt();
        final int dropShadowYOffset = random.nextInt();
        //when
        final ServiceCloverProperties config = new ServiceCloverProperties();
        config.issueDir = issueDir;
        config.width = width;
        config.height = height;
        config.dpi = dpi;
        config.dropShadowXOffset = dropShadowXOffset;
        config.dropShadowYOffset = dropShadowYOffset;
        //then
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(config.getIssueDir()).isEqualTo(issueDir);
            s.assertThat(config.getWidth()).isEqualTo(width);
            s.assertThat(config.getHeight()).isEqualTo(height);
            s.assertThat(config.getDpi()).isEqualTo(dpi);
            s.assertThat(config.getDropShadowXOffset()).isEqualTo(dropShadowXOffset);
            s.assertThat(config.getDropShadowYOffset()).isEqualTo(dropShadowYOffset);
        });
    }
}