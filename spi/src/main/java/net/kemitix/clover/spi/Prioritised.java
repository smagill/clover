package net.kemitix.clover.spi;

import java.util.Comparator;

public interface Prioritised {
    int getPriority();

    static Comparator<Prioritised> byPriority() {
        return Comparator.comparing(Prioritised::getPriority);
    }

}
