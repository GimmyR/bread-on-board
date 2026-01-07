package mg.breadOnBoard.controller;

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

import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.dto.SignUpForm;
import mg.breadOnBoard.model.Recipe;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RecipeRestControllerIntegrationTestH2 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testCreate() throws JsonProcessingException, Exception {
		
		MvcResult result = mockMvc.perform(post("/api/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new SignUpForm("johndoe", "johndoe@example.com", "pwdJohn"))))
				.andExpect(status().isCreated())
				.andReturn();
		
		mockMvc.perform(post("/api/recipe/create")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelette", "2 Eggs, oil, salt"))))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testEdit() throws JsonProcessingException, Exception {
		
		MvcResult result = mockMvc.perform(post("/api/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new SignUpForm("johndoe", "johndoe@example.com", "pwdJohn"))))
				.andExpect(status().isCreated())
				.andReturn();
		
		MvcResult result2 = mockMvc.perform(post("/api/recipe/create")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelette", "2 Eggs, oil, salt"))))
				.andExpect(status().isCreated())
				.andReturn();
		
		Recipe recipe = objectMapper.readValue(result2.getResponse().getContentAsString(), Recipe.class);
		
		mockMvc.perform(post("/api/recipe/edit/" + recipe.getId())
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelet", "3 Eggs, oil, salt"))))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		
		MvcResult result = mockMvc.perform(post("/api/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new SignUpForm("johndoe", "johndoe@example.com", "pwdJohn"))))
				.andExpect(status().isCreated())
				.andReturn();
		
		MvcResult result2 = mockMvc.perform(post("/api/recipe/create")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelette", "2 Eggs, oil, salt"))))
				.andExpect(status().isCreated())
				.andReturn();
		
		Recipe recipe = objectMapper.readValue(result2.getResponse().getContentAsString(), Recipe.class);
		
		mockMvc.perform(post("/api/recipe/delete/" + recipe.getId())
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString()))
				.andExpect(status().isCreated());
		
	}

}
