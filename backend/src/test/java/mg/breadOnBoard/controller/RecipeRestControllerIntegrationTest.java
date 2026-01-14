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

import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RecipeRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RecipeService recipeService;
	
	@Test
	@WithMockUser(username = "adawong")
	public void testCreate() throws JsonProcessingException, Exception {
		
		mockMvc.perform(post("/api/recipe/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelette", "2 Eggs, oil, salt"))))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	@WithMockUser(username = "adawong")
	public void testEdit() throws JsonProcessingException, Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		mockMvc.perform(post("/api/recipe/edit/" + recipes.iterator().next().getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelet", "3 Eggs, oil, salt"))))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	@WithMockUser(username = "adawong")
	public void testDelete() throws JsonProcessingException, Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		mockMvc.perform(post("/api/recipe/delete/" + recipes.iterator().next().getId()))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testGetAll() throws Exception {
		
		mockMvc.perform(get("/api/recipes"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetOne() throws Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		mockMvc.perform(get("/api/recipes/" + recipes.iterator().next().getId()))
				.andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser(username = "adawong")
	public void testIsAuthor() throws Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		mockMvc.perform(get("/api/recipe/author/" + recipes.iterator().next().getId()))
				.andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser(username = "adawong")
	public void testGetMyRecipes() throws Exception {
		
		mockMvc.perform(get("/api/my-recipes"))
				.andExpect(status().isOk());
		
	}

}
