package mg.breadOnBoard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.RecipeForm;
import mg.breadOnBoard.dto.RecipeResponse;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Account;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.repository.RecipeRepository;

@Service
@AllArgsConstructor
@Transactional
public class RecipeService {
	
	private RecipeRepository recipeRepository;
	private SequenceService sequenceService;
	
	public Iterable<Recipe> findAll(String search) {
		
		if(search != null)
			return this.findAllByTitleOrIngredients(search);
		
		return recipeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Iterable<Recipe> findAllByAccountId(String accountId) {
		
		return recipeRepository.findByAccountId(accountId, Sort.by(Sort.Direction.ASC, "accountId"));
		
	}
	
	public Iterable<Recipe> findAllByTitleOrIngredients(String search) {
		
		String toSearch = "%" + search + "%";
		
		return recipeRepository.findByTitleLikeOrIngredientsLike(toSearch, toSearch, Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Recipe findOneById(String id) {
		
		Optional<Recipe> opt = recipeRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new NotFoundException("Recipe not found");
		
	}
	
	public Recipe findByIdAndAccountId(String id, String accountId) {
		
		Recipe recipe = recipeRepository.findOneByIdAndAccountId(id, accountId);
		
		if(recipe == null)
			throw new NotFoundException("Recipe not found");
		
		return recipe;
		
	}
	
	public Recipe create(Account account, RecipeForm form) {
		
		Recipe recipe = new Recipe(sequenceService.generateRecipeID(), account, form.title(), null, form.ingredients(), null);
		return recipeRepository.save(recipe);
		
	}
	
	public Recipe update(Account account, String recipeId, RecipeForm form) {
		
		Recipe recipe = this.findByIdAndAccountId(recipeId, account.getId());
		recipe.editTitle(form.title());
		recipe.editIngredients(form.ingredients());
		return recipeRepository.save(recipe);
		
	}
	
	public Recipe save(Recipe recipe) {
		
		return recipeRepository.save(recipe);
		
	}
	
	public void delete(Recipe recipe) {
		
		recipeRepository.delete(recipe);
		
	}
	
	public Iterable<RecipeResponse> mapToResponse(Iterable<Recipe> recipes) {
		
		List<RecipeResponse> results = new ArrayList<RecipeResponse>();
		
		recipes.forEach(recipe -> {
			
			RecipeResponse res = new RecipeResponse(
					
					recipe.getId(), 
					recipe.getTitle(), 
					recipe.getIngredients(), 
					recipe.getImage()
					
			); results.add(res);
			
		}); return results;
		
	}

}
