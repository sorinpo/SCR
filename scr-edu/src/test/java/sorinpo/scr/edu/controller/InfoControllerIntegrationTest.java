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
public class InfoControllerIntegrationTest {
	
	static String USERNAME = "ag";
	static String PATH = "/infos.json";
	static int YEAR = 2013;

	@Test
	public void getInfo() throws Exception {
		
		User user = User.findUsersByUsernameEquals(USERNAME).getSingleResult();
		setAuthentication(USERNAME);
		
		getMockMvc().perform(
			get(PATH)
				.header("Accept","application/json")
				.param("username", USERNAME)
				.param("year", String.valueOf(YEAR)))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=utf-8"))
	        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
	        .andExpect(jsonPath("$.year").value(YEAR));
		
	}

	@Test
	@Transactional
	@Rollback
	public void createAndUpdateInfo() throws Exception {
		
		User user = User.findUsersByUsernameEquals(USERNAME).getSingleResult();
		setAuthentication(USERNAME);
		
		ValueHolder<Long> idHold = new ValueHolder<Long>();
		ValueHolder<Integer> versionHold = new ValueHolder<Integer>();
		
		String innerObj = "\"year\":"+YEAR+",\"userId\":"+user.getId()+",\"beneficiariIndirecti\":{\"feb\":\"1\",\"mar\":\"3\"},\"voluntariImplicati\":{\"feb\":\"2\",\"mar\":\"4\"},\"intalniriGrupuriLucru\":{\"feb\":\"0\",\"mar\":\"0\"},\"participantiGrupuriLucru\":{\"feb\":\"0\",\"mar\":\"0\"},\"conferinteOrganizate\":{\"feb\":\"0\",\"mar\":\"0\"},\"participantiConferinte\":{\"feb\":\"0\",\"mar\":\"0\"},\"aparitiiPresa\":{\"feb\":\"0\",\"mar\":\"0\"},\"links\":\"testlink\"";
		
		getMockMvc().perform(
			post(PATH)
				.header("Accept","application/json")
				.param("username", USERNAME)
				.param("year", String.valueOf(YEAR))
				.content("{" + innerObj + " }"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=utf-8"))
        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
        .andExpect(jsonPath("$.year").value(YEAR))
        .andExpect(jsonPath("$.beneficiariIndirecti.feb").value(1))
        .andExpect(jsonPath("$.id").value( steal(idHold) ))
		.andExpect(jsonPath("$.version").value( steal(versionHold) ))
		.andExpect(jsonPath("$.links").value( "testlink" ));
		
		getMockMvc().perform(
				put(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.param("year", String.valueOf(YEAR))
					.content("{  id: "+idHold.getValue()+", version: "+versionHold.getValue()+", " + innerObj.replaceAll("\"feb\":\"1\"", "\"feb\":\"11\"").replaceAll("testlink", "testlink2") + " }"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
            .andExpect(jsonPath("$.year").value(YEAR))
            .andExpect(jsonPath("$.beneficiariIndirecti.feb").value(11))
			.andExpect(jsonPath("$.id").value( idHold.getValue() ))
			.andExpect(jsonPath("$.version").value( versionHold.getValue()+1 ));
		
		getMockMvc().perform(
				get(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.param("year", String.valueOf(YEAR)))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType("application/json;charset=utf-8"))
		        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
		        .andExpect(jsonPath("$.year").value(YEAR))
		        .andExpect(jsonPath("$.id").value( idHold.getValue() ))
		        .andExpect(jsonPath("$.version").value( versionHold.getValue()+1 ))
		        .andExpect(jsonPath("$.links").value( "testlink2" ));
		
	}
	
	@Test
	@Transactional
	@Rollback
	public void twoPostsOneRead() throws Exception {
		User user = User.findUsersByUsernameEquals(USERNAME).getSingleResult();
		setAuthentication(USERNAME);
		
		String content = "{ \"year\":"+YEAR+",\"userId\":"+user.getId()+",\"beneficiariIndirecti\":{\"feb\":\"1\",\"mar\":\"3\"},\"voluntariImplicati\":{\"feb\":\"2\",\"mar\":\"4\"},\"intalniriGrupuriLucru\":{\"feb\":\"0\",\"mar\":\"0\"},\"participantiGrupuriLucru\":{\"feb\":\"0\",\"mar\":\"0\"},\"conferinteOrganizate\":{\"feb\":\"0\",\"mar\":\"0\"},\"participantiConferinte\":{\"feb\":\"0\",\"mar\":\"0\"},\"aparitiiPresa\":{\"feb\":\"0\",\"mar\":\"0\"},\"links\":\"testlink\" }";
		
		getMockMvc().perform(
				post(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.param("year", String.valueOf(YEAR))
					.content(content))
	        .andExpect(status().isOk());
	        
		getMockMvc().perform(
				post(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.param("year", String.valueOf(YEAR))
					.content(content))
	        .andExpect(status().isOk());
		
		getMockMvc().perform(
				get(PATH)
					.header("Accept","application/json")
					.param("username", USERNAME)
					.param("year", String.valueOf(YEAR)))
		        .andExpect(status().isOk())
		        .andExpect(content().contentType("application/json;charset=utf-8"))
		        .andExpect(jsonPath("$.userId").value(user.getId().intValue()))
		        .andExpect(jsonPath("$.year").value(YEAR));
	}

}