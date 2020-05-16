package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.awt.*;

@ApplicationScoped
public class FrontCoverBlock extends AbstractBlock {

    @Getter private final int priority = 30;

    @Getter
    @Inject @FrontCover Instance<Element<Graphics2D>> elements;

}
