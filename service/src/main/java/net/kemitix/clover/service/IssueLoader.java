package net.kemitix.clover.service;


import net.kemitix.clover.spi.CloverConfig;
import net.kemitix.files.FileReader;
import net.kemitix.files.FileReaderWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class IssueLoader {

    private static final Logger LOGGER =
            Logger.getLogger(
                    IssueLoader.class.getName());

    @Produces
    @ApplicationScoped
    public FileReader fileReader() {
        return new FileReaderWriter();
    }

    @Produces
    @ApplicationScoped
    public Issue loadIssueJson(
            final CloverConfig config,
            final FileReader fileReader,
            final Jsonb jsonb
    ) throws IOException {
        final Path cloverJsonPath = Paths.get(config.getIssueDir(), config.getConfigFile());
        LOGGER.info("Reading: " + cloverJsonPath);
        final String json = fileReader.read(cloverJsonPath.toFile());
        return jsonb.fromJson(json, Issue.class);
    }

}
