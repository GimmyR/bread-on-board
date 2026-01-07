package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.RecipeResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeStepRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testGetAllByRecipeId() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/api/recipes"))
				.andExpect(status().isOk())
				.andReturn();
		
		List<RecipeResponse> recipes = objectMapper.readValue(
				result.getResponse().getContentAsString(), 
				new TypeReference<List<RecipeResponse>>() {});
		
		mockMvc.perform(get("/api/recipe-steps/" + recipes.getFirst().id()))
				.andExpect(status().isOk());
		
	}

}
