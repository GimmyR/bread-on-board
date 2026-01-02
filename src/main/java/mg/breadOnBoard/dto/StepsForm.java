package mg.breadOnBoard.dto;

import mg.breadOnBoard.model.RecipeStep;

public record StepsForm(
		String recipeId, 
		Iterable<RecipeStep> steps
) {}
