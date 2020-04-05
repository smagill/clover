package net.kemitix.clover.images;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.kemitix.clover.spi.images.FontFace;

import javax.enterprise.context.Dependent;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

@Dependent
public class FontCache {

    private static final Logger LOGGER =
            Logger.getLogger(
                    FontCache.class.getName());

    private final Map<File, Font> fileCache = new HashMap<>();

    private final Map<FontAndSize, Font> fontcache = new HashMap<>();

    private final FontLoader fontLoader;

    public FontCache(final FontLoader fontLoader) {
        this.fontLoader = fontLoader;
    }

    public Font loadFont(final FontFace fontFace) {
        LOGGER.finest(String.format("Requesting %s %d",
                fontFace.getFont(), fontFace.getSize()));
        final Font baseFont =
                fileCache.computeIfAbsent(
                        fontFace.getFont(),
                        loadNewFontFile(fontFace));
        return fontcache.computeIfAbsent(
                FontAndSize.of(baseFont, fontFace.getSize()),
                resizeFont());
    }

    private Function<File, Font> loadNewFontFile(
            final FontFace fontFace
    ) {
        return file -> {
            LOGGER.fine(String.format("Loading %s", fontFace.getFont()));
            return fontLoader.loadFont(fontFace);
        };
    }


    private Function<FontAndSize, Font> resizeFont() {
        return fontAndSize -> {
            LOGGER.finer(String.format("Resizing %s to %d",
                    fontAndSize.getFont().getName(), fontAndSize.getSize()));
            return fontAndSize.font
                    .deriveFont(Font.PLAIN, fontAndSize.getSize());
        };
    }

    @Getter
    @EqualsAndHashCode
    private static class FontAndSize {
        private final Font font;
        private final int size;

        private FontAndSize(final Font font, final int size) {
            this.font = font;
            this.size = size;
        }

        static FontAndSize of(final Font font, final int size) {
            return new FontAndSize(font, size);
        }
    }
}
