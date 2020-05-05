package net.kemitix.clover.service;

import net.kemitix.files.FileReader;
import net.kemitix.files.FileReaderWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class IssueLoaderTest {

    private final CloverConfigProperties cloverConfig =
            new CloverConfigProperties();
    private final String issueNumber = UUID.randomUUID().toString();
    private final Jsonb jsonb = JsonbBuilder.create();
    private final IssueLoader issueLoader = new IssueLoader();
    private final FileReader fileReader;

    public IssueLoaderTest(@Mock FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Test
    @DisplayName("Loads the clover.json file")
    public void loadIssueJson() throws IOException {
        //given
        cloverConfig.issueDir = "dir";
        cloverConfig.configFile = "clover.json";
        final String content =
                String.format("{" +
                        "\"issue\":\"%s\"," +
                        "\"title-colour\":\"red\"," +
                        "\"sub-title-colour\":\"yellow\"" +
                        "}", issueNumber);
        given(fileReader.read(any())).willReturn(content);
        //when
        final Issue issue = issueLoader.loadIssueJson(cloverConfig, fileReader, jsonb);
        //then
        assertThat(issue.getIssue()).isEqualTo(issueNumber);
        assertThat(issue.getTitleColour()).isEqualTo("red");
        assertThat(issue.getSubTitleColour()).isEqualTo("yellow");
    }
}
