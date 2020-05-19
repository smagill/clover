package net.kemitix.clover.image.effects;

import lombok.*;
import net.kemitix.clover.spi.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.awt.*;
import java.util.function.Consumer;

@With
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OpaqueFillImpl implements OpaqueFill<Graphics2D>,
        Effect.RegionNext<Graphics2D>,
        OpaqueFill.ColourNext<Graphics2D> {

    @Inject Colours colours;

    private Region region;
    private String colour;
    private double opacity;

    @Override
    public void accept(Graphics2D graphics2D) {
        graphics2D.setPaint(colours.getColor(colour, opacity));
        graphics2D.fillRect(region.getLeft(), region.getTop(),
                region.getWidth(), region.getHeight());
    }

    @Override
    public Consumer<Graphics2D> region(Region region) {
        return withRegion(region);
    }

    @Override
    public ColourNext<Graphics2D> opacity(double opacity) {
        return withOpacity(opacity);
    }

    @Override
    public RegionNext<Graphics2D> colour(String colour) {
        return withColour(colour);
    }
}
