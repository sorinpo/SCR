package sorinpo.scr.edu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/webmvc-config.xml", "classpath:META-INF/spring/applicationContext.xml"})
@ActiveProfiles("test")
public class ConfigControllerTest {
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void getConfig() throws Exception {
		this.mockMvc.perform(get("/config").header("Accept","application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=utf-8"))
        .andExpect(jsonPath("$.activeYear").value(2013))
        .andExpect(jsonPath("$.activeMonths.jan").value(true))
        .andExpect(jsonPath("$.activeMonths.may").value(false));
	}

	@Test
	public void updateConfig() throws Exception {
		this.mockMvc.perform(post("/config").header("Accept","application/json").content("{ 'activeYear': 2014, 'activeMonths': {'may': true} }"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=utf-8"))
        .andExpect(jsonPath("$.activeYear").value(2014))
        .andExpect(jsonPath("$.activeMonths.jan").value(false))
        .andExpect(jsonPath("$.activeMonths.may").value(true));
	}

}