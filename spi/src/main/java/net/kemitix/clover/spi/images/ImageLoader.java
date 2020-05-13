package net.kemitix.clover.spi.images;

import java.io.File;
import java.io.IOException;

public interface ImageLoader {
    Image load(File file) throws IOException;
}
