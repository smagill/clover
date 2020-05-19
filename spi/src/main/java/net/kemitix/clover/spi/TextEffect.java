package net.kemitix.clover.spi;

public interface TextEffect<T> extends Effect<T> {

    TextNext<T> fontFace(FontFace fontFace);
    interface TextNext<T> { VAlignNext<T> text(String text);}
    interface VAlignNext<T> { HAlignNext<T> vAlign(VAlignment vAlignment);}
    interface HAlignNext<T> { RegionNext<T> hAlign(HAlignment hAlignment);}

    enum HAlignment {
        LEFT,
        RIGHT,
        CENTRE,
    }

    enum VAlignment {
        TOP,
        BOTTOM,
        CENTRE,
    }

}
