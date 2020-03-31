package net.kemitix.cossmass.clover;

import net.kemitix.files.FileReaderWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class IssueLoaderTest {

    private final CloverConfigProperties cloverConfig =
            new CloverConfigProperties();
    private final String issueNumber = UUID.randomUUID().toString();
    private final Jsonb jsonb = JsonbBuilder.create();
    private final IssueLoader issueLoader =
            new IssueLoader(cloverConfig, jsonb);
    @TempDir
    Path directory;

    @Test
    @DisplayName("Loads the clover.json file")
    public void loadIssueJson() throws IOException {
        //given
        final String content =
                String.format("{\"issue\":\"%s\"}}", issueNumber);
        new FileReaderWriter()
                .write(
                        directory.resolve("clover.json").toFile(),
                        content);
        cloverConfig.issueDir = directory.toString();
        //when
        final Issue issue = issueLoader.loadIssueJson();
        //then
        assertThat(issue.getIssue()).isEqualTo(issueNumber);
    }

    @Test
    @DisplayName("When clover.json is missing throws an exception")
    public void whenMissingThenThrow() {
        cloverConfig.issueDir = directory.toString();
        //then
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(issueLoader::loadIssueJson);
    }
}