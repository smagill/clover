package net.kemitix.clover.spi;

import net.kemitix.fontface.FontFace;

public interface TextEffect<T> extends Effect<T> {

    WrapFitOrTextNext<T> fontFace(FontFace fontFace);
    interface WrapFitOrTextNext<T> extends FitOrTextNext<T> { TextNext<T> wrap();}
    interface FitOrTextNext<T> extends TextNext<T> { TextNext<T> fit();}
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
