package sorinpo.scr.edu;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

privileged aspect CommonControllerIntegrationTestAspect {
    
	private interface CommonControllerIntegrationTestMixin {
		public void setup();
    	public MockMvc getMockMvc();
    }
    
    declare parents : sorinpo.scr.edu..*ControllerIntegrationTest implements CommonControllerIntegrationTestMixin; 
    
    @Autowired
    private WebApplicationContext CommonControllerIntegrationTestMixin.wac;
    private MockMvc CommonControllerIntegrationTestMixin.mockMvc;
    
    @Before
    public void CommonControllerIntegrationTestMixin.setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	public MockMvc CommonControllerIntegrationTestMixin.getMockMvc() {
		return this.mockMvc;
	}
	
}
