package sorinpo.scr.edu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sorinpo.scr.edu.util.TestHelpers.setAuthentication;
import static sorinpo.scr.edu.util.TestHelpers.steal;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sorinpo.scr.edu.TestContextConfig;
import sorinpo.scr.edu.model.User;
import sorinpo.scr.edu.util.TestHelpers.ValueHolder;

@ContextConfiguration(classes=TestContextConfig.class)
public class CentruInfoControllerIntegrationTest {
	
	static String USERNAME = "ag";
	static String PATH = "/centru_infos.json";
	
	@Test
	public void getCentruInfo() throws Exception {
		
		User user = User.findUsersByUsernameEquals(USERNAME).getSingleResult();
		setAuthentication(USERNAME);
		
		getMockMvc().perform(
			get(PATH)
				.header("Accept","application/json")
				.param("username", USERNAME))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=utf-8"))
	        .andExpect(jsonPath("$.userId").value(user.getId().intValue()));
	}

	@Test
	@Transactional
	@Rollback
	public void createAndUpdateCentruInfo() throws Exception {
		
		User user = User.findUsersByUsernameEquals(USERNAME).getSingleResult();
		setAuthentication(USERNAME);
		
		ValueHolder<Long> idHold = new ValueHolder<Long>();
		ValueHolder<Integer> versionHold = new ValueHolder<Integer>();
		
		getMockMvc().perform(
			post(PATH)
				.header("Accept","application/json")
				.param("username", USERNAME)
				.content("{ 'userId': "+user.getId()+", address: \"address\", locality: \"locality\", team: \"team\" }"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=utf-8"))
        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
        .andExpect(jsonPath("$.address").value("address"))
        .andExpect(jsonPath("$.locality").value("locality"))
        .andExpect(jsonPath("$.team").value("team"))
		.andExpect(jsonPath("$.id").value( steal(idHold) ))
		.andExpect(jsonPath("$.version").value( steal(versionHold) ));
		
		getMockMvc().perform(
				put(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.content("{ 'userId': "+user.getId()+", address: \"address1\", locality: \"locality1\", team: \"team1\", id: "+idHold.getValue()+", version: "+versionHold.getValue()+" }"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=utf-8"))
	        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
	        .andExpect(jsonPath("$.address").value("address1"))
	        .andExpect(jsonPath("$.locality").value("locality1"))
	        .andExpect(jsonPath("$.team").value("team1"))
			.andExpect(jsonPath("$.id").value( idHold.getValue() ))
			.andExpect(jsonPath("$.version").value( versionHold.getValue()+1 ));
		
		getMockMvc().perform(
				get(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType("application/json;charset=utf-8"))
		        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
		        .andExpect(jsonPath("$.id").value( idHold.getValue() ))
				.andExpect(jsonPath("$.version").value( versionHold.getValue()+1 ));
		
	}
	
	@Test
	@Transactional
	@Rollback
	public void twoPostsOneRead() throws Exception {
		User user = User.findUsersByUsernameEquals(USERNAME).getSingleResult();
		setAuthentication(USERNAME);
		
		getMockMvc().perform(
				post(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.content("{ 'userId': "+user.getId()+", address: \"address\", locality: \"locality\", team: \"team\" }"))
	        .andExpect(status().isOk());
	        
		getMockMvc().perform(
				post(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.content("{ 'userId': "+user.getId()+", address: \"address\", locality: \"locality\", team: \"team\" }"))
	        .andExpect(status().isOk());
		
		getMockMvc().perform(
				get(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType("application/json;charset=utf-8"))
		        .andExpect(jsonPath("$.userId").value(user.getId().intValue()));
	}

}