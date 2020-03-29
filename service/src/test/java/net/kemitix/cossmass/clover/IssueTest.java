package net.kemitix.cossmass.clover;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IssueTest {

    private final Issue issue = new Issue();
    private final String value = UUID.randomUUID().toString();

    @Test
    void coverArt() {
        issue.setCoverArt(value);
        assertThat(issue.coverArt())
                .startsWith("/cossmass/")
                .endsWith(value)
                .hasSize("/cossmass/".length() + value.length());
    }

}