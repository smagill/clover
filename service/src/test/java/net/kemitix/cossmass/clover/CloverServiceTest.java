package net.kemitix.cossmass.clover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.enterprise.inject.Instance;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CloverServiceTest {

    private final Issue issue = new Issue();
    @Mock
    private Instance<CloverFormat> formats;
    @Mock
    private CloverFormat formatAlpha;
    @Mock
    private CloverFormat formatBeta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void writesEachFormat() {
        //given
        assertThat(formats).isNotNull();
        given(formats.stream())
                .willReturn(Stream.<CloverFormat>builder()
                        .add(formatAlpha)
                        .add(formatBeta).build());
        final CloverService service = new CloverService(issue, formats);
        //when
        service.run();
        //then
        verify(formatAlpha).write();
        verify(formatBeta).write();
    }

}