package net.kemitix.clover.spi;

import net.kemitix.fontface.FontCache;
import net.kemitix.fontface.FontLoader;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class FontFaceProvider {

    @Produces
    FontCache fontCache(FontLoader fontLoader) {
        return new FontCache(fontLoader);
    }

    @Produces
    FontLoader fontLoader() {
        return new FontLoader();
    }

}
