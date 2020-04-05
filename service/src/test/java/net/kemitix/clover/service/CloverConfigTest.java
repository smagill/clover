package net.kemitix.clover.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CloverConfigTest {

    private final CloverConfigProperties cloverConfig =
            new CloverConfigProperties();

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
    @DisplayName("Height has been converted from inches to PX")
    public void heightIsInPX() {
        //given
        final Random random = new Random();
        final int height = random.nextInt();
        final int inchesToPX = random.nextInt();
        cloverConfig.height = height;
        cloverConfig.inchesToPX = inchesToPX;
        //when
        final int result = cloverConfig.height();
        //then
        assertThat(result)
                .isEqualTo(height * inchesToPX);
    }

    @Test
    @DisplayName("Width has been converted from inches to PX")
    public void widthIsInPX() {
        //given
        final Random random = new Random();
        final int width = random.nextInt();
        final int inchesToPX = random.nextInt();
        cloverConfig.width = width;
        cloverConfig.inchesToPX = inchesToPX;
        //when
        final int result = cloverConfig.width();
        //then
        assertThat(result)
                .isEqualTo(width * inchesToPX);
    }

    @Test
    @DisplayName("Get plain values")
    public void getPlainValues() {
        //given
        final String baseDir = UUID.randomUUID().toString();
        final String issueDir = UUID.randomUUID().toString();
        final Random random = new Random();
        final int width = random.nextInt();
        final int height = random.nextInt();
        final int inchesToPX = random.nextInt();
        final int dropShadowXOffset = random.nextInt();
        final int dropShadowYOffset = random.nextInt();
        //when
        final CloverConfigProperties config = new CloverConfigProperties();
        config.baseDir = baseDir;
        config.issueDir = issueDir;
        config.width = width;
        config.height = height;
        config.inchesToPX = inchesToPX;
        config.dropShadowXOffset = dropShadowXOffset;
        config.dropShadowYOffset = dropShadowYOffset;
        //then
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(config.getBaseDir()).isEqualTo(baseDir);
            s.assertThat(config.getIssueDir()).isEqualTo(issueDir);
            s.assertThat(config.getWidth()).isEqualTo(width);
            s.assertThat(config.getHeight()).isEqualTo(height);
            s.assertThat(config.getInchesToPX()).isEqualTo(inchesToPX);
            s.assertThat(config.getDropShadowXOffset()).isEqualTo(dropShadowXOffset);
            s.assertThat(config.getDropShadowYOffset()).isEqualTo(dropShadowYOffset);
        });
    }
}