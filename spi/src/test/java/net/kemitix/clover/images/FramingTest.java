package net.kemitix.clover.images;


import net.kemitix.clover.spi.Area;
import net.kemitix.clover.spi.FatalCloverError;
import net.kemitix.clover.spi.Framing;
import net.kemitix.clover.spi.XY;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FramingTest {

    @Test
    @DisplayName("When inner matches outer, center at 0,0")
    public void innerIsOuter() {
        //given
        final Area area = Area.of(100, 100);
        final Framing framing = Framing.of(area, area);
        //when
        final XY centered = framing.centered();
        //then
        assertThat(centered).isEqualTo(XY.at(0, 0));
    }

    @Test
    @DisplayName("When inner taller than outer, throw")
    public void innerTallerThanOuter() {
        //given
        final Area inner = Area.of(100, 100);
        final Area outer = Area.of(100, 10);
        final Framing framing =
                Framing.builder().inner(inner).outer(outer).build();
        //then
        assertThatExceptionOfType(FatalCloverError.class)
                .isThrownBy(framing::centered)
                .withMessageStartingWith("Inner is taller than outer");
    }

    @Test
    @DisplayName("When inner wider than outer, throw")
    public void innerWiderThanOuter() {
        //given
        final Area inner = Area.of(100, 100);
        final Area outer = Area.of(10, 100);
        final Framing framing =
                Framing.builder().inner(inner).outer(outer).build();
        //then
        assertThatExceptionOfType(FatalCloverError.class)
                .isThrownBy(framing::centered)
                .withMessageStartingWith("Inner is wider than outer");
    }

    @Test
    @DisplayName("When inner fits within outer, center")
    public void innerFitsWithinOuter() {
        //given
        final Area inner = Area.of(50, 50);
        final Area outer = Area.of(100, 100);
        final Framing framing =
                Framing.builder().inner(inner).outer(outer).build();
        //when
        final XY centered = framing.centered();
        //then
        assertThat(centered).isEqualTo(XY.at(25, 25));
    }
}