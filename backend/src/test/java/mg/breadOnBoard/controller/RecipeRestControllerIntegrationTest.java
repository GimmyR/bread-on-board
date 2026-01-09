package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.AccountForm;
import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.dto.RecipeResponse;
import mg.breadOnBoard.dto.RecipeStepForm;
import mg.breadOnBoard.dto.SignUpForm;
import mg.breadOnBoard.dto.StepsForm;
import mg.breadOnBoard.model.Recipe;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RecipeRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testCreate() throws JsonProcessingException, Exception {
		
		MvcResult result = this.signUp();
		this.createRecipe(result);
		
	}
	
	private MvcResult signUp() throws JsonProcessingException, Exception {
		
		return mockMvc.perform(post("/api/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new SignUpForm("johndoe", "johndoe@example.com", "pwdJohn"))))
				.andExpect(status().isCreated())
				.andReturn();
		
	}
	
	private MvcResult createRecipe(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException, Exception {
		
		return mockMvc.perform(post("/api/recipe/create")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelette", "2 Eggs, oil, salt"))))
				.andExpect(status().isCreated())
				.andReturn();
		
	}
	
	@Test
	public void testEdit() throws JsonProcessingException, Exception {
		
		MvcResult result = this.signUp();
		MvcResult result2 = this.createRecipe(result);
		this.editRecipe(result, result2);
		
	}
	
	private MvcResult editRecipe(MvcResult result, MvcResult result2) throws Exception {
		
		Recipe recipe = objectMapper.readValue(result2.getResponse().getContentAsString(), Recipe.class);
		
		return mockMvc.perform(post("/api/recipe/edit/" + recipe.getId())
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new RecipeForm("Omelet", "3 Eggs, oil, salt"))))
				.andExpect(status().isCreated())
				.andReturn();
		
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		
		MvcResult result = this.signUp();
		MvcResult result2 = this.createRecipe(result);
		this.deleteRecipe(result, result2);
		
	}
	
	private MvcResult deleteRecipe(MvcResult result, MvcResult result2) throws UnsupportedEncodingException, Exception {
		
		Recipe recipe = objectMapper.readValue(result2.getResponse().getContentAsString(), Recipe.class);
		
		return mockMvc.perform(post("/api/recipe/delete/" + recipe.getId())
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString()))
				.andExpect(status().isCreated())
				.andReturn();
		
	}
	
	@Test
	public void testGetAll() throws Exception {
		
		MvcResult result = this.signUp();
		this.createRecipe(result);
		this.getAll();
		
	}
	
	private MvcResult getAll() throws Exception {
		
		return mockMvc.perform(get("/api/recipes"))
			.andExpect(status().isOk())
			.andReturn();
		
	}
	
	@Test
	public void testGetOne() throws Exception {
		
		MvcResult result = this.signUp();
		this.createRecipe(result);
		MvcResult result2 = this.getAll();
		this.getOne(result2);
		
	}
	
	private MvcResult getOne(MvcResult result) throws Exception {
		
		List<RecipeResponse> recipes = objectMapper.readValue(
				result.getResponse().getContentAsString(), 
				new TypeReference<List<RecipeResponse>>() {});
		
		return mockMvc.perform(get("/api/recipes/" + recipes.getFirst().id()))
			.andExpect(status().isOk())
			.andReturn();
		
	}
	
	@Test
	public void testIsAuthor() throws Exception {
		
		MvcResult result = this.signUp();
		this.createRecipe(result);
		MvcResult result2 = this.getAll();
		MvcResult result3 = this.login();
		this.isAuthor(result2, result3);
		
	}
	
	private MvcResult isAuthor(MvcResult result, MvcResult result2) throws UnsupportedEncodingException, Exception {
		
		List<RecipeResponse> recipes = objectMapper.readValue(
				result.getResponse().getContentAsString(), 
				new TypeReference<List<RecipeResponse>>() {});
		
		return mockMvc.perform(get("/api/recipe/author/" + recipes.getFirst().id())
				.header("Authorization", "Bearer " + result2.getResponse().getContentAsString()))
				.andExpect(status().isOk())
				.andReturn();
		
	}
	
	private MvcResult login() throws JsonProcessingException, Exception {
		
		return mockMvc.perform(post("/api/sign-in")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new AccountForm("johndoe", "pwdJohn"))))
				.andExpect(status().isOk())
				.andReturn();
		
	}
	
	@Test
	public void testGetMyRecipes() throws Exception {
		
		MvcResult result = this.signUp();
		MvcResult result2 = this.createRecipe(result);
		this.saveSteps(result, result2);
		this.getMyRecipes(result);
		
	}
	
	private MvcResult getMyRecipes(MvcResult result) throws UnsupportedEncodingException, Exception {
		
		return mockMvc.perform(get("/api/my-recipes")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString()))
				.andExpect(status().isOk())
				.andReturn();
		
	}
	
	private MvcResult saveSteps(MvcResult result, MvcResult result2) throws JsonProcessingException, UnsupportedEncodingException, Exception {
		
		RecipeResponse recipe = objectMapper.readValue(result2.getResponse().getContentAsString(), RecipeResponse.class);
		List<RecipeStepForm> steps = new ArrayList<RecipeStepForm>();
		steps.add(new RecipeStepForm("Break an egg and put the contents in a bowl."));
		steps.add(new RecipeStepForm("Do it for the second."));
		StepsForm form = new StepsForm(recipe.id(), steps);
		
		return mockMvc.perform(post("/api/recipe-steps/save")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(form)))
				.andExpect(status().isCreated())
				.andReturn();
		
	}

}
