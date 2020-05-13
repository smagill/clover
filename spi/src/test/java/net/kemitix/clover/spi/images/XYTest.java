package net.kemitix.clover.spi.images;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class XYTest
        implements WithAssertions {

    @Test
    @DisplayName("Rotate an XY ClockWise")
    public void rotateCWAsExpected() {
        //given
        XY xy = XY.at(10,20);;
        XY expected = XY.at(-20, 10);
        //when
        XY result = xy.rotateCW();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Rotate an XY Counter-ClockWise")
    public void rotateCCWAsExpected() {
        //given
        XY xy = XY.at(-20, 10);
        XY expected = XY.at(10,20);;
        //when
        XY result = xy.rotateCCW();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Flip XY Vertically around x-axis")
    public void flipVerticalAxis() {
        //given
        XY xy = XY.at(10, -20);
        XY expected = XY.at(10, 20);
        //when
        XY result = xy.flipVertically(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Flip XY Vertically around off-x-axis")
    public void flipVerticalOffAxis() {
        //given
        XY xy = XY.at(10, -20);
        XY expected = XY.at(10, 120);
        //when
        XY result = xy.flipVertically(50);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Flip XY Horizontally around y-axis")
    public void flipHorizontalAxis() {
        //given
        XY xy = XY.at(20, 10);
        XY expected = XY.at(-20, 10);
        //when
        XY result = xy.flipHorizontally(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Flip XY Horizontally around off-y-axis")
    public void flipHorizontalOffAxis() {
        //given
        XY xy = XY.at(20, 10);
        XY expected = XY.at(80, 10);
        //when
        XY result = xy.flipHorizontally(50);
        //then
        assertThat(result).isEqualTo(expected);
    }
}