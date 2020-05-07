package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.clover.spi.images.Image;
import net.kemitix.clover.spi.images.ImageService;
import net.kemitix.files.FileReader;
import net.kemitix.files.FileReaderWriter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class IssueConfigLoader {

    private static final Logger LOGGER =
            Logger.getLogger(
                    IssueConfigLoader.class.getName());

    @Produces
    @ApplicationScoped
    public FileReader fileReader() {
        return new FileReaderWriter();
    }

    @Produces
    @ApplicationScoped
    public IssueConfig loadIssueJson(
            final CloverProperties config,
            final FileReader fileReader,
            final Jsonb jsonb
    ) throws IOException {
        final Path cloverJsonPath =
                Paths.get(config.getIssueDir(), config.getConfigFile());
        LOGGER.info("Reading: " + cloverJsonPath);
        final String json = fileReader.read(cloverJsonPath.toFile());
        return jsonb.fromJson(json, IssueConfig.class);
    }

    @Produces
    @ApplicationScoped
    public Image coverArtImage(
            final CloverProperties cloverProperties,
            final IssueConfig issueConfig,
            final ImageService imageService
    ) throws IOException {
        final Path coverArtPath = Paths.get(
                cloverProperties.getIssueDir(),
                issueConfig.getCoverArt());
        return imageService.load(coverArtPath.toFile());
    }

}
