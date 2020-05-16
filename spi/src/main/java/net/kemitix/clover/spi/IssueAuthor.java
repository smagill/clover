package net.kemitix.clover.spi;

import java.util.Arrays;

public interface IssueAuthor {
    String getForename();
    String getSurname();

    default String authorName() {
        return String.join(" ", Arrays.asList(
                getForename(), getSurname()));
    }
}
