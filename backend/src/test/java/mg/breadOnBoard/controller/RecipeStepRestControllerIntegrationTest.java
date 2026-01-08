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
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.dto.RecipeResponse;
import mg.breadOnBoard.dto.RecipeStepForm;
import mg.breadOnBoard.dto.SignUpForm;
import mg.breadOnBoard.dto.StepsForm;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RecipeStepRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testSaveAll() throws JsonProcessingException, Exception {
		
		MvcResult result = this.signUp();
		MvcResult result2 = this.createRecipe(result);
		this.saveSteps(result, result2);
		
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
	public void testGetAllByRecipeId() throws Exception {
		
		MvcResult result = this.signUp();
		MvcResult result2 = this.createRecipe(result);
		MvcResult result3 = this.saveSteps(result, result2);
		this.getAllByRecipeId(result3);
		
	}
	
	private MvcResult getAllByRecipeId(MvcResult result) throws Exception {
		
		RecipeResponse recipe = objectMapper.readValue(result.getResponse().getContentAsString(), RecipeResponse.class);
		
		return mockMvc.perform(get("/api/recipe-steps/" + recipe.id()))
				.andExpect(status().isOk())
				.andReturn();
		
	}

}
