package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.AccountForm;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testLogIn() throws Exception {
		
		mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("johndoe", "pwdJohn"))))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetUsername() throws Exception {
		
		MvcResult result = mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("johndoe", "pwdJohn"))))
				.andExpect(status().isOk())
				.andReturn();
		
		String token = result.getResponse().getContentAsString();
		
		mockMvc.perform(get("/api/username")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
		
	}

}
