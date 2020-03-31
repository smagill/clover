package net.kemitix.cossmass.clover;


import net.kemitix.cossmass.clover.images.CloverConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class IssueLoader {

    private static final Logger LOGGER =
            Logger.getLogger(
                    IssueLoader.class.getName());

    private final CloverConfig config;
    private final Jsonb jsonb;

    public IssueLoader(
            final CloverConfig config,
            final Jsonb jsonb
    ) {
        this.config = config;
        this.jsonb = jsonb;
    }

    @Produces
    @ApplicationScoped
    public Issue loadIssueJson() throws FileNotFoundException {
        final Path cloverJsonPath = Paths.get(config.getIssueDir(), config.getConfigFile());
        LOGGER.info("Reading: " + cloverJsonPath);
        final FileReader reader = new FileReader(cloverJsonPath.toFile());
        return jsonb.fromJson(reader, Issue.class);
    }

}
