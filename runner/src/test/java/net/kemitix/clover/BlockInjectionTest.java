package net.kemitix.clover;

import io.quarkus.test.junit.QuarkusTest;
import net.kemitix.clover.issue.*;
import net.kemitix.clover.spi.*;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.awt.*;

/**
 * Regression test to ensure {@code @Inject Instance<Block<Graphics2D>>}
 * injects all the blocks expected.
 */
@QuarkusTest
public class BlockInjectionTest
        implements WithAssertions {

    @Inject Instance<Block<Graphics2D>> blocks;
    @Inject @FrontCover Instance<Element<Graphics2D>> frontPageElements;
    @Inject @BackCover Instance<Element<Graphics2D>> backPageElements;
    @Inject @Spine Instance<Element<Graphics2D>> spineElements;

    @Test
    @DisplayName("Expected Blocks are @Injected")
    public void expectedBlocksAreInjected() {
        assertThat(blocks.stream().count()).isEqualTo(3);
        assertThat(blocks.stream())
                .anyMatch(block -> block instanceof FrontCoverBlock);
        assertThat(blocks.stream())
                .anyMatch(block -> block instanceof BackCoverBlock);
        assertThat(blocks.stream())
                .anyMatch(block -> block instanceof SpineBlock);
    }

    @Test
    @DisplayName("Expected Front Page Elements are @Injected")
    public void expectedFrontPageElementsAreInjected() {
        assertThat(frontPageElements.stream().count()).isEqualTo(3);
        assertThat(frontPageElements.stream())
                .anyMatch(e -> e instanceof AuthorsElement);
        assertThat(frontPageElements.stream())
                .anyMatch(e -> e instanceof CoverLogo);
        assertThat(frontPageElements.stream())
                .anyMatch(e -> e instanceof LogoStraps);
    }

    @Test
    @DisplayName("Expected Back Page Elements are @Injected")
    public void expectedBackPageElementsAreInjected() {
        assertThat(backPageElements.stream().count()).isEqualTo(3);
        assertThat(backPageElements.stream())
                .anyMatch(e -> e instanceof StoriesSF);
        assertThat(backPageElements.stream())
                .anyMatch(e -> e instanceof StoriesFantasy);
        assertThat(backPageElements.stream())
                .anyMatch(e -> e instanceof StoriesReprints);
    }

    @Test
    @DisplayName("Expected Spine Elements are @Injected")
    public void expectedSpineElementsAreInjected() {
        assertThat(spineElements.stream().count()).isEqualTo(2);
        assertThat(spineElements.stream())
                .anyMatch(e -> e instanceof SpineText);
        assertThat(spineElements.stream())
                .anyMatch(e -> e instanceof SpineArea);
    }

}
