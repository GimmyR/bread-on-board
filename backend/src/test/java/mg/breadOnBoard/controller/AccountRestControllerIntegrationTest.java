package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
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
		
		mockMvc.perform(post("/api/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new SignUpForm("johndoe", "johndoe@example.com", "pwdJohn"))))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testLogIn() throws Exception {
		
		mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("adawong", "pwdAda"))))
				.andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser(username = "adawong")
	public void testGetUsername() throws Exception {
		
		mockMvc.perform(get("/api/username"))
				.andExpect(status().isOk());
		
	}

}
