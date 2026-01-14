package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import mg.breadOnBoard.dto.RecipeStepForm;
import mg.breadOnBoard.dto.StepsForm;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RecipeStepRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RecipeService recipeService;
	
	@Test
	@WithMockUser(username = "adawong")
	public void testSaveAll() throws JsonProcessingException, Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		List<RecipeStepForm> steps = new ArrayList<RecipeStepForm>();
		steps.add(new RecipeStepForm("Break an egg and put the contents in a bowl."));
		steps.add(new RecipeStepForm("Do it for the second."));
		StepsForm form = new StepsForm(recipes.iterator().next().getId(), steps);
		
		mockMvc.perform(post("/api/recipe-steps/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(form)))
				.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testGetAllByRecipeId() throws Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		mockMvc.perform(get("/api/recipe-steps/" + recipes.iterator().next().getId()))
			.andExpect(status().isOk());
		
	}

}
