package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.AccountForm;
import mg.breadOnBoard.dto.SignUpForm;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AccountRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testSignUp() throws JsonProcessingException, Exception {
		
		this.signUp();
		
	}
	
	private MvcResult signUp() throws JsonProcessingException, Exception {
		
		return mockMvc.perform(post("/api/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new SignUpForm("johndoe", "johndoe@example.com", "pwdJohn"))))
				.andExpect(status().isCreated())
				.andReturn();
		
	}
	
	@Test
	public void testLogIn() throws Exception {
		
		this.signUp();
		this.login();
		
	}
	
	private MvcResult login() throws JsonProcessingException, Exception {
		
		return mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("johndoe", "pwdJohn"))))
				.andExpect(status().isOk())
				.andReturn();
		
	}
	
	@Test
	public void testGetUsername() throws Exception {
		
		this.signUp();
		MvcResult result = this.login();
		this.getUsername(result);
		
	}
	
	private MvcResult getUsername(MvcResult result) throws Exception {
		
		String token = result.getResponse().getContentAsString();
		
		return mockMvc.perform(get("/api/username")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andReturn();
		
	}

}
