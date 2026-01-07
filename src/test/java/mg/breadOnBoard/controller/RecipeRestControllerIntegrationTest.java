package mg.breadOnBoard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetAll() throws Exception {
		
		mockMvc.perform(get("/api/recipes"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetOne() throws Exception {
		
		mockMvc.perform(get("/api/recipes/R00005"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testIsAuthor() throws Exception {
		
		mockMvc.perform(get("/api/recipe/author/R00005")
				.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzY3MzQ0MDIzfQ.DvFkCRJ5aaSNNi8jRg1kbTl6dfWxKks8Q-v2G0EVi_vZWvAy7mPalRiuJMg-ALBl6mEDmFIAGXJEnlvhbvSqyw"))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetMyRecipes() throws Exception {
		
		mockMvc.perform(get("/api/my-recipes")
				.header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzY3MzQ0MDIzfQ.DvFkCRJ5aaSNNi8jRg1kbTl6dfWxKks8Q-v2G0EVi_vZWvAy7mPalRiuJMg-ALBl6mEDmFIAGXJEnlvhbvSqyw"))
				.andExpect(status().isOk());
		
	}

}
