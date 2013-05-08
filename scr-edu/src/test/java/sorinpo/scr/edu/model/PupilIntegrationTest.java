package sorinpo.scr.edu.model;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import sorinpo.scr.edu.TestContextConfig;

@ContextConfiguration(classes=TestContextConfig.class)
@RooIntegrationTest(entity = Pupil.class)
public class PupilIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
