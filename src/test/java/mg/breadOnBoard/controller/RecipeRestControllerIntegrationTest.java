package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.AccountForm;
import mg.breadOnBoard.dto.RecipeResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testGetAll() throws Exception {
		
		mockMvc.perform(get("/api/recipes"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetOne() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/api/recipes"))
				.andExpect(status().isOk())
				.andReturn();
		
		List<RecipeResponse> recipes = objectMapper.readValue(
				result.getResponse().getContentAsString(), 
				new TypeReference<List<RecipeResponse>>() {});
		
		mockMvc.perform(get("/api/recipes/" + recipes.getFirst().id()))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testIsAuthor() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/api/recipes"))
				.andExpect(status().isOk())
				.andReturn();
		
		List<RecipeResponse> recipes = objectMapper.readValue(
				result.getResponse().getContentAsString(), 
				new TypeReference<List<RecipeResponse>>() {});
		
		MvcResult result2 = mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("johndoe", "pwdJohn"))))
				.andExpect(status().isOk())
				.andReturn();
		
		mockMvc.perform(get("/api/recipe/author/" + recipes.getFirst().id())
				.header("Authorization", "Bearer " + result2.getResponse().getContentAsString()))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetMyRecipes() throws Exception {
		
		MvcResult result = mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("johndoe", "pwdJohn"))))
				.andExpect(status().isOk())
				.andReturn();
		
		mockMvc.perform(get("/api/my-recipes")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString()))
				.andExpect(status().isOk());
		
	}

}
