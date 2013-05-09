package sorinpo.scr.edu;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

privileged aspect CommonControllerIntegrationTest {
    
	private interface ICommonControllerIntegrationTest {
    	public void setup();
    	public MockMvc getMockMvc();
    }
    
    declare parents : sorinpo.scr.edu..*ControllerIntegrationTest implements ICommonControllerIntegrationTest; 
    
    @Autowired
    private WebApplicationContext ICommonControllerIntegrationTest.wac;
    private MockMvc ICommonControllerIntegrationTest.mockMvc;
    
    @Before
	public void ICommonControllerIntegrationTest.setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	public MockMvc ICommonControllerIntegrationTest.getMockMvc() {
		return this.mockMvc;
	}
    
}
