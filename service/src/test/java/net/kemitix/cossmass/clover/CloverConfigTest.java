package net.kemitix.cossmass.clover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

}