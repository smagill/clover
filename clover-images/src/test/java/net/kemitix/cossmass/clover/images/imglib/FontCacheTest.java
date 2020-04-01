package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.FontFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class FontCacheTest {

    private final File fontFile = new File("font.otf");
    private final String colour = "colour";
    @Mock
    private FontLoader fontLoader;
    @Mock
    private Font loadedFont;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(fontLoader.loadFont(any())).willReturn(loadedFont);
    }

    @Test
    @DisplayName("When cache is empty then load font")
    public void emptyCache() {
        //given
        final FontCache fontCache = new FontCache(fontLoader);
        final FontFace fontFace = FontFace.of(fontFile, 26, colour);
        //when
        fontCache.loadFont(fontFace);
        //then
        verify(fontLoader).loadFont(fontFace);
    }

    @Test
    @DisplayName("When cache has font, but not size, then don't reload font")
    public void notInSize() {
        //given
        final FontCache fontCache = new FontCache(fontLoader);
        final FontFace previousFontFace = FontFace.of(fontFile, 16, colour);
        fontCache.loadFont(previousFontFace);
        final FontFace fontFace = FontFace.of(fontFile, 26, colour);
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
        final FontCache fontCache = new FontCache(fontLoader);
        final FontFace fontFace = FontFace.of(fontFile, 26, colour);
        //when
        fontCache.loadFont(fontFace);
        fontCache.loadFont(fontFace);
        //then
        verify(fontLoader, times(1)).loadFont(fontFace);
    }
}