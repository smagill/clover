package net.kemitix.clover.spi;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface IssueStories {
    List<? extends IssueStory> getSf();

    List<? extends IssueStory> getFantasy();

    List<? extends IssueStory> getReprint();


    default Stream<IssueStory> stream() {
        return Stream.of(getSf(), getFantasy(), getReprint())
                .flatMap(Collection::stream);
    }
}
