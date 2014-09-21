package sorinpo.scr.edu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sorinpo.scr.edu.TestContextConfig;

@ContextConfiguration(classes=TestContextConfig.class)
public class ConfigControllerIntegrationTest {

	@Test
	public void getConfig() throws Exception {
		getMockMvc().perform(get("/config").header("Accept","application/json"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=utf-8"))
        .andExpect(jsonPath("$.activeYear").value(2013))
        .andExpect(jsonPath("$.activeMonths.jan").value(true))
        .andExpect(jsonPath("$.activeMonths.may").value(false));
	}

	@Test
	@Transactional
	@Rollback
	public void updateConfig() throws Exception {
		getMockMvc().perform(post("/config").header("Accept","application/json").content("{ 'activeYear': 2014, 'activeMonths': {'may': true} }"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=utf-8"))
        .andExpect(jsonPath("$.activeYear").value(2014))
        .andExpect(jsonPath("$.activeMonths.jan").value(false))
        .andExpect(jsonPath("$.activeMonths.may").value(true));
	}

}