package net.kemitix.clover.service;

import net.kemitix.clover.spi.CloverProperties;
import net.kemitix.files.FileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class IssueConfigLoaderTest {

    private final CloverProperties cloverConfig;
    private final String issueNumber = UUID.randomUUID().toString();
    private final Jsonb jsonb = JsonbBuilder.create();
    private final IssueConfigLoader issueLoader = new IssueConfigLoader();
    private final FileReader fileReader;

    public IssueConfigLoaderTest(
            @Mock CloverProperties cloverConfig,
            @Mock FileReader fileReader
    ) {
        this.cloverConfig = cloverConfig;
        this.fileReader = fileReader;
    }

    @Test
    @DisplayName("Loads the clover.json file")
    public void loadIssueJson() throws IOException {
        //given
        given(cloverConfig.getIssueDir()).willReturn("dir");
        given(cloverConfig.getConfigFile()).willReturn("clover.json");
        final String content =
                String.format("{" +
                        "\"issue\":\"%s\"," +
                        "\"title-colour\":\"red\"," +
                        "\"sub-title-colour\":\"yellow\"" +
                        "}", issueNumber);
        given(fileReader.read(any())).willReturn(content);
        //when
        final IssueConfig issue =
                issueLoader.loadIssueJson(cloverConfig, fileReader, jsonb);
        //then
        assertThat(issue.getIssue()).isEqualTo(issueNumber);
        assertThat(issue.getTitleColour()).isEqualTo("red");
        assertThat(issue.getSubTitleColour()).isEqualTo("yellow");
    }
}