package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class RecipeStepRestControllerIntegrationTestH2 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testSaveAll() throws JsonProcessingException, Exception {
		
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
		
		RecipeResponse recipe = objectMapper.readValue(result2.getResponse().getContentAsString(), RecipeResponse.class);
		List<RecipeStepForm> steps = new ArrayList<RecipeStepForm>();
		steps.add(new RecipeStepForm("Break an egg and put the contents in a bowl."));
		steps.add(new RecipeStepForm("Do it for the second."));
		StepsForm form = new StepsForm(recipe.id(), steps);
		
		mockMvc.perform(post("/api/recipe-steps/save")
				.header("Authorization", "Bearer " + result.getResponse().getContentAsString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(form)))
				.andExpect(status().isCreated());
		
	}

}
