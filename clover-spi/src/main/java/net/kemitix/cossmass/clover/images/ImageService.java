package net.kemitix.cossmass.clover.images;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    Image load(File file) throws IOException;
}
