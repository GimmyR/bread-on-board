package mg.breadOnBoard.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.exception.FileIsEmptyException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.service.AccountService;
import mg.breadOnBoard.service.ImageService;
import mg.breadOnBoard.service.RecipeService;
import mg.breadOnBoard.service.RecipeStepService;

@RestController
@AllArgsConstructor
public class RecipeRestController {
	
	private RecipeService recipeService;
	private RecipeStepService recipeStepService;
	private ImageService imageService;
	private AccountService accountService;
	
	@GetMapping("/api/recipe/get-all/")
	public Iterable<Recipe> getAll() {
		
		return recipeService.findAll();
		
	}
	
	@PostMapping("/api/recipe/search/")
	public Iterable<Recipe> search(@RequestParam String search) {
		
		return recipeService.findAllByTitleOrIngredients(search);
		
	}
	
	@GetMapping("/api/recipe/get-one/{id}")
	public ResponseEntity<Recipe> getOne(@PathVariable String id) {
		
		Recipe recipe = recipeService.findOneById(id);
		return ResponseEntity.status(HttpStatus.OK).body(recipe);
		
	}
	
	@PostMapping("/api/recipe/create")
	public ResponseEntity<String> create(@RequestHeader("Authorization") String authorization, @RequestParam String title, @RequestParam MultipartFile image, @RequestParam String ingredients) {
		
		ResponseEntity<String> response = null;
		Recipe recipe = null;
		
		try {
			
			Account account = accountService.getAccountByJWT(authorization);
			imageService.upload(image);
			recipe = new Recipe(null, account.getId(), title, image.getOriginalFilename(), ingredients);
			recipe = recipeService.save(recipe);
			response = ResponseEntity.status(HttpStatus.CREATED).body(recipe.getId());
			
		} catch(IOException e) {
			
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		} return response;
		
	}
	
	@PostMapping("/api/recipe/edit/{id}")
	public ResponseEntity<String> edit(@RequestHeader("Authorization") String authorization, @PathVariable String id, @RequestParam String title, @RequestParam MultipartFile image, @RequestParam String ingredients) {
		
		ResponseEntity<String> response = null;
		Recipe recipe = new Recipe();
		
		try {
			
			recipe = this.tryToEdit(authorization, recipe, id, title, image, ingredients);
			response = ResponseEntity.status(HttpStatus.OK).body(recipe.getId());
			
		} catch (IOException e) {

			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		} return response;
		
	}
	
	private Recipe tryToEdit(String authorization, Recipe recipe, String id, String title, MultipartFile image, String ingredients) throws NotFoundException, FileIsEmptyException, IOException {
		
		Account account = accountService.getAccountByJWT(authorization);
		recipe = recipeService.findByIdAndAccountId(id, account.getId());
		recipe.editTitle(title);
		recipe.editIngredients(ingredients);
		
		if((!image.isEmpty()) && (!image.getOriginalFilename().equals(recipe.getImage()))) {

			String oldImage = recipe.getImage();
			recipe.editImage(image.getOriginalFilename());
			imageService.upload(image);
			imageService.delete(oldImage);
			
		} return recipeService.save(recipe);
		
	}
	
	@PostMapping("/api/recipe/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
		
		ResponseEntity<String> response = null;
		
		try {
				
			Account account = accountService.getAccountByJWT(authorization);
			Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
			recipeStepService.deleteAllByRecipeId(id);
			recipeService.delete(recipe);
			imageService.delete(recipe.getImage());
			response = ResponseEntity.status(HttpStatus.CREATED).body("Recipe is successfully removed");
		
		} catch (IOException e) {
			
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		} return response;
		
	}
	
	@GetMapping("/api/recipe/author/{id}")
	public ResponseEntity<Boolean> isAuthor(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
		
		Account account = accountService.getAccountByJWT(authorization);
		Recipe recipe = recipeService.findByIdAndAccountId(id, account.getId());
		return ResponseEntity.status(HttpStatus.OK).body((recipe != null) && (recipe.getId().equals(id)));
		
	}

}
