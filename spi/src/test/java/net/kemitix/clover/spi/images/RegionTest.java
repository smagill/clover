package net.kemitix.clover.spi.images;

import net.kemitix.clover.spi.Region;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

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

    @Test
    @DisplayName("Region with modified width")
    public void regionWithModifiedWidth() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(20).height(10)
                .build();
        //when
        Region result = region.withWidth(width -> width * 2);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with modified height")
    public void regionWithModifiedHeight() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(10).height(20)
                .build();
        //when
        Region result = region.withHeight(height -> height * 2);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with modified top")
    public void regionWithModifiedTop() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(20)
                .width(10).height(10)
                .build();
        //when
        Region result = region.withTop(top -> top * 2);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with modified left")
    public void regionWithModifiedLeft() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(20).top(10)
                .width(10).height(10)
                .build();
        //when
        Region result = region.withLeft(left -> left * 2);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with modified bottom")
    public void regionWithModifiedBottom() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                // bottom == 20
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(10).height(30)
                // bottom == 40
                .build();
        //when
        Region result = region.withBottom(bottom -> bottom * 2);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with modified right")
    public void regionWithModifiedRight() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                // right == 20
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(30).height(10)
                // right == 40
                .build();
        //when
        Region result = region.withRight(right -> right * 2);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with fixed modified top")
    public void regionWithFixedModifiedTop() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(20)
                .width(10).height(10)
                .build();
        //when
        Region result = region.withTop(20);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with fixed modified bottom")
    public void regionWithFixedModifiedBottom() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(10).height(20)
                .build();
        //when
        Region result = region.withBottom(30);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with fixed modified left")
    public void regionWithFixedModifiedLeft() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(20).top(10)
                .width(10).height(10)
                .build();
        //when
        Region result = region.withLeft(20);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with fixed modified right")
    public void regionWithFixedModifiedRight() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(20).height(10)
                .build();
        //when
        Region result = region.withRight(30);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with fixed modified width")
    public void regionWithFixedModifiedWidth() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(20).height(10)
                .build();
        //when
        Region result = region.withWidth(20);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Region with fixed modified height")
    public void regionWithFixedModifiedHeight() {
        //given
        Region region = Region.builder()
                .left(10).top(10)
                .width(10).height(10)
                .build();
        Region expected = Region.builder()
                .left(10).top(10)
                .width(10).height(20)
                .build();
        //when
        Region result = region.withHeight(20);
        //then
        assertThat(result).isEqualTo(expected);
    }
}