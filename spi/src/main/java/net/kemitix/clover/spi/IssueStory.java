package net.kemitix.clover.spi;

public interface IssueStory {
    IssueAuthor getAuthor();

    String getTitle();

    String getSample();
}
