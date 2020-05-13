package net.kemitix.clover.spi.images;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegionTest
        implements WithAssertions {

    @Test
    @DisplayName("Rotate a Region ClockWise")
    public void rotateCWAsExpected() {
        //given
        Region region = Region.builder()
                .top(20).left(10)
                .width(30).height(40)
                .build();
        Region expected =Region.builder()
                .top(10).left(-60)
                .width(40).height(30)
                .build();
        //when
        Region result = region.rotateCW();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Rotate a Region Counter-ClockWise")
    public void rotateCCWAsExpected() {
        //given
        Region region =Region.builder()
                .top(10).left(-60)
                .width(40).height(30)
                .build();
        Region expected = Region.builder()
                .top(20).left(10)
                .width(30).height(40)
                .build();
        //when
        Region result = region.rotateCCW();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Flip Region Vertically around x-axis")
    public void flipVerticalAxis() {
        //given
        Region region =Region.builder()
                .left(-20).top(10)
                .width(40).height(30)
                .build();
        Region expected = Region.builder()
                .left(-20).top(-10)
                .width(40).height(30)
                .build();
        //when
        Region result = region.flipVertically(0);
        //then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    @DisplayName("Flip Region Vertically around off-x-axis")
    public void flipVerticalOffAxis() {
        //given
        Region region =Region.builder()
                .left(-20).top(10)
                .width(40).height(30)
                .build();
        Region expected = Region.builder()
                .left(-20).top(90)
                .width(40).height(30)
                .build();
        //when
        Region result = region.flipVertically(50);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Flip Region Horizontally around y-axis")
    public void flipHorizontalAxis() {
        //given
        Region region = Region.builder()
                .left(-20).top(10)
                .width(40).height(30)
                .build();
        Region expected = Region.builder()
                .left(20).top(10)
                .width(40).height(30)
                .build();
        //when
        Region result = region.flipHorizontally(0);
        //then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    @DisplayName("Flip Region Horizontally around off-y-axis")
    public void flipHorizontalOffAxis() {
        //given
        Region region =Region.builder()
                .left(-20).top(10)
                .width(40).height(30)
                .build();
        Region expected = Region.builder()
                .left(120).top(10)
                .width(40).height(30)
                .build();
        //when
        Region result = region.flipHorizontally(50);
        //then
        assertThat(result).isEqualTo(expected);
    }
}