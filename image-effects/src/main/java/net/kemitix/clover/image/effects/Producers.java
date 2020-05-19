package net.kemitix.clover.image.effects;

import net.kemitix.text.fit.BoxFitter;
import net.kemitix.text.fit.TextFit;
import net.kemitix.text.fit.WordWrapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class Producers {

    @Produces
    @ApplicationScoped
    WordWrapper wordWrapper() {
        return TextFit.wrapper();
    }

    @Produces
    @ApplicationScoped
    BoxFitter boxFitter() {
        return TextFit.fitter();
    }
}
