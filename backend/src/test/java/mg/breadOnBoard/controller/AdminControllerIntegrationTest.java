package mg.breadOnBoard.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AdminControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RecipeService recipeService;
	
	@Test
	@WithMockUser(username = "admin", roles = "Admin")
	public void testRemoveRecipe() throws JsonProcessingException, Exception {
		
		Iterable<Recipe> recipes = recipeService.findAll(null);
		
		mockMvc.perform(post("/recipe/remove/" + recipes.iterator().next().getId())
				.with(csrf()))
				.andExpect(status().is(302));
		
	}
	
	@Test
	public void testSignIn_GET() throws Exception {
		
		mockMvc.perform(get("/sign-in"))
				.andExpect(view().name("base"))
				.andExpect(model().attribute("view", "sign-in/index"))
				.andExpect(model().attribute("title", "Login"));
		
	}
	
	@Test
	public void testSignIn_POST() throws Exception {
		
		mockMvc.perform(post("/sign-in")
				.with(csrf())
				.param("username", "admin")
				.param("password", "pwd456"))
				.andExpect(status().is(302));
		
	}
	
	@Test
	@WithMockUser(username = "admin", roles = "Admin")
	public void testGetHome() throws Exception {
		
		mockMvc.perform(get("/"))
				.andExpect(view().name("base"))
				.andExpect(model().attribute("view", "home/index"))
				.andExpect(model().attribute("title", "Home"));
		
	}

}
