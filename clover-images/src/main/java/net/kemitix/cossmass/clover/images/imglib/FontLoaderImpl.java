package net.kemitix.cossmass.clover.images.imglib;

import net.kemitix.cossmass.clover.images.FatalCloverError;
import net.kemitix.cossmass.clover.images.FontFace;

import javax.enterprise.context.Dependent;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
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
        LOGGER.info(String.format("Loading %s", fontFace.getFont()));
        final Map<TextAttribute, Object> map = new HashMap<>();
        map.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
        map.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, fontFace.getFont())
                    .deriveFont(map);
        } catch (final FontFormatException | IOException e) {
            throw new FatalCloverError("Font load error", e);
        }
    }

}
