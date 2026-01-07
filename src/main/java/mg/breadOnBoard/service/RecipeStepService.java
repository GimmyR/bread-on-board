package mg.breadOnBoard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import mg.breadOnBoard.dto.RecipeStepForm;
import mg.breadOnBoard.dto.RecipeStepResponse;
import mg.breadOnBoard.exception.NotFoundException;
import mg.breadOnBoard.model.Recipe;
import mg.breadOnBoard.model.RecipeStep;
import mg.breadOnBoard.repository.RecipeStepRepository;

@Service
@AllArgsConstructor
@Transactional
public class RecipeStepService {
	
	private RecipeStepRepository recipeStepRepository;
	private SequenceService sequenceService;
	private EntityManager entityManager;
	
	public Iterable<RecipeStep> findAllByRecipeId(String recipeId) {
		
		return recipeStepRepository.findAllByRecipeId(recipeId, Sort.by(Sort.Direction.ASC, "order"));
		
	}
	
	public RecipeStep findOneById(String id) {
		
		Optional<RecipeStep> opt = recipeStepRepository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		
		else throw new NotFoundException("Recipe steps not fund");
		
	}
	
	public RecipeStep save(RecipeStep step) {
		
		if(step.getId() == null) {
			
			String id = sequenceService.generateRecipeStepID();
			step.editId(id);
			
		} return recipeStepRepository.save(step);
		
	}
	
	public void saveAll(Recipe recipe, Iterable<RecipeStepForm> steps) {
		
		this.deleteAllByRecipeId(recipe.getId());
		
		int order = 1;
		
		for(RecipeStepForm step : steps) {
			
			RecipeStep recipeStep = new RecipeStep(null, recipe, order, step.text());
			this.save(recipeStep);
			order++;
			
		}
		
	}
	
	public void saveAll_2(Recipe recipe, Iterable<RecipeStepForm> steps) {
		
		this.deleteAllByRecipe(recipe);
		
		int order = 1;
		
		for(RecipeStepForm step : steps) {
			
			RecipeStep recipeStep = new RecipeStep(null, recipe, order, step.text());
			this.save(recipeStep);
			order++;
			
		}
		
	}
	
	public void delete(RecipeStep step) {
		
		recipeStepRepository.delete(step);
		
	}
	
	public void deleteAllByRecipe(Recipe recipe) {
		
		recipeStepRepository.deleteAllByRecipeId(recipe.getId());
		
	}
	
	public void deleteAllByRecipeId(String recipeId) {
		
		entityManager.createQuery("DELETE FROM RecipeStep r WHERE r.recipe.id = :id")
				        .setParameter("id", recipeId)
				        .executeUpdate();
		
	}
	
	public Iterable<RecipeStepResponse> mapAllToGetAllByRecipeId(Iterable<RecipeStep> steps) {
		
		List<RecipeStepResponse> result = new ArrayList<RecipeStepResponse>();
		
		steps.forEach(step -> {
			
			result.add(new RecipeStepResponse(step.getId(), step.getText()));
			
		}); return result;
		
	}

}
