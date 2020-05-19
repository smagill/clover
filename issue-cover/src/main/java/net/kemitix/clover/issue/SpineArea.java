package net.kemitix.clover.issue;

import lombok.Getter;
import net.kemitix.clover.spi.*;
import net.kemitix.properties.typed.TypedProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.logging.Logger;

@Spine
@ApplicationScoped
public class SpineArea implements Element<Graphics2D> {

    private static final Logger LOG =
            Logger.getLogger(
                    SpineArea.class.getName());

    @Getter private final int priority = 10;

    @Inject IssueDimensions dimensions;
    @Inject @Spine Paint paint;

    @Override
    public void draw(Graphics2D graphics2D, TypedProperties typedProperties) {
        Region region = dimensions.getSpineCrop();
        LOG.info("Draw Spine background: " + region);
        graphics2D.setPaint(paint);
        graphics2D.fillRect(
                region.getLeft(), region.getTop(),
                region.getWidth(), region.getHeight());
    }
}
