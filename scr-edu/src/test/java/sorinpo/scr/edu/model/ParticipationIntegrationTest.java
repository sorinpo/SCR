package sorinpo.scr.edu.model;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;

import sorinpo.scr.edu.TestContextConfig;

@ContextConfiguration(classes=TestContextConfig.class)
@RooIntegrationTest(entity = Participation.class)
public class ParticipationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
