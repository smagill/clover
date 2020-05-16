package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.awt.*;

@ApplicationScoped
public class BackCoverBlock extends AbstractBlock {

    @Getter private final int priority = 20;

    @Getter
    @Inject @BackCover Instance<Element<Graphics2D>> elements;

}
