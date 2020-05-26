package net.kemitix.clover;

import io.quarkus.runtime.Startup;
import net.kemitix.clover.story.card.StoryCardProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@Startup
@ApplicationScoped
public class ConfigLogging {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ConfigLogging.class.getName());

    @Inject StoryCardProperties properties;

    @PostConstruct
    public void init() {


        LOGGER.info(String.format("story-card enabled: %b", properties.isEnabled()));
        LOGGER.info(String.format("story-card width: %d", properties.getWidth()));
        LOGGER.info(String.format("story-card height: %d", properties.getHeight()));
        LOGGER.info(String.format("story-card padding: %d", properties.getPadding()));
        LOGGER.info(String.format("story-card font size: %d", properties.getLogoFontSize()));
    }

}
