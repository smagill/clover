package net.kemitix.clover.images;

import net.kemitix.clover.spi.FontLoader;
import net.kemitix.clover.spi.FontFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.io.File;
import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class CloverFontCacheTest {

    private final URI fontLocation = new File("font.otf").toURI();
    private final String colour = "colour";
    @Mock
    private FontLoader fontLoader;
    @Mock
    private Font loadedFont;

    @BeforeEach
    public void setUp() {
        given(fontLoader.loadFont(any())).willReturn(loadedFont);
    }

    @Test
    @DisplayName("When cache is empty then load font")
    public void emptyCache() {
        //given
        final CloverFontCache fontCache = new CloverFontCache();
        fontCache.fontLoader = fontLoader;
        final FontFace fontFace = FontFace.of(fontLocation, 26, colour);
        //when
        fontCache.loadFont(fontFace);
        //then
        verify(fontLoader).loadFont(fontFace);
    }

    @Test
    @DisplayName("When cache has font, but not size, then don't reload font")
    public void notInSize() {
        //given
        final CloverFontCache fontCache = new CloverFontCache();
        fontCache.fontLoader = fontLoader;
        final FontFace previousFontFace = FontFace.of(fontLocation, 16, colour);
        fontCache.loadFont(previousFontFace);
        final FontFace fontFace = FontFace.of(fontLocation, 26, colour);
        //when
        fontCache.loadFont(fontFace);
        //then
        verify(fontLoader, times(1)).loadFont(previousFontFace);
        verifyNoMoreInteractions(fontLoader);
    }

    @Test
    @DisplayName("When cache has font in size, then don't load font")
    public void available() {
        //given
        final CloverFontCache fontCache = new CloverFontCache();
        fontCache.fontLoader = fontLoader;
        final FontFace fontFace = FontFace.of(fontLocation, 26, colour);
        //when
        fontCache.loadFont(fontFace);
        fontCache.loadFont(fontFace);
        //then
        verify(fontLoader, times(1)).loadFont(fontFace);
    }
}