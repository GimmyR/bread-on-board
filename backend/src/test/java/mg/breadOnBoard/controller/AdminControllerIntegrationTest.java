package mg.breadOnBoard.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.dto.RecipeResponse;
import mg.breadOnBoard.dto.SignUpForm;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AdminControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@WithMockUser(username = "admin", roles = "Admin")
	public void testRemoveRecipe() throws JsonProcessingException, Exception {
		
		MvcResult result = this.signUp();
		MvcResult result2 = this.createRecipe(result);
		this.removeRecipe(result2);
		
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
	
	private MvcResult removeRecipe(MvcResult result) throws Exception {
		
		RecipeResponse recipe = objectMapper.readValue(result.getResponse().getContentAsString(), RecipeResponse.class);
		
		return mockMvc.perform(post("/recipe/remove/" + recipe.id())
				.with(csrf()))
				.andExpect(status().is(302))
				.andReturn();
		
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
