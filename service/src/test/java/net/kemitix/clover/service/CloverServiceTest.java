package net.kemitix.clover.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.enterprise.inject.Instance;

//import org.junit.jupiter.api.Test;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;

public class CloverServiceTest {

    private final Issue issue = new Issue();
    @Mock
    private Instance<AbstractCloverFormat> formats;
    @Mock
    private AbstractCloverFormat formatAlpha;
    @Mock
    private AbstractCloverFormat formatBeta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void writesEachFormat() {
//        //given
//        assertThat(formats).isNotNull();
//        given(formats.stream())
//                .willReturn(Stream.<AbstractCloverFormat>builder()
//                        .add(formatAlpha)
//                        .add(formatBeta).build());
//        final CloverService service = new CloverService(issue, formats);
//        //when
//        service.run();
//        //then
//        verify(formatAlpha).write();
//        verify(formatBeta).write();
//    }

}