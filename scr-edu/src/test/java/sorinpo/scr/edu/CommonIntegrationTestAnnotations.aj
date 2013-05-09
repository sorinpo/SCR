package sorinpo.scr.edu;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

privileged aspect CommonIntegrationTestAnnotations {
    
	declare @type: sorinpo.scr.edu..*IntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
	declare @type: sorinpo.scr.edu..*IntegrationTest: @Configurable;
    declare @type: sorinpo.scr.edu..*IntegrationTest: @WebAppConfiguration;
    declare @type: sorinpo.scr.edu..*IntegrationTest: @ActiveProfiles("test");
    
}
