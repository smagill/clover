package net.kemitix.cossmass.clover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CloverConfigTest {

    private final CloverConfig cloverConfig = new CloverConfig();

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

}