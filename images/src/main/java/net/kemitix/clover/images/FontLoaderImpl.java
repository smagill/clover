package net.kemitix.clover.images;

import net.kemitix.clover.spi.FatalCloverError;
import net.kemitix.clover.spi.FontLoader;
import net.kemitix.clover.spi.FontFace;

import javax.enterprise.context.Dependent;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Dependent
public class FontLoaderImpl implements FontLoader {

    private static final Logger LOGGER =
            Logger.getLogger(
                    FontLoaderImpl.class.getName());

    @Override
    public Font loadFont(final FontFace fontFace) {
        URI fontUri = fontFace.getFontLocation();
        LOGGER.info(String.format("Loading %s", fontUri));
        final Map<TextAttribute, Object> map = new HashMap<>();
        map.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
        map.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontUri))
                    .deriveFont(map);
        } catch (final FontFormatException | IOException e) {
            throw new FatalCloverError("Font load error", e);
        }
    }

}
