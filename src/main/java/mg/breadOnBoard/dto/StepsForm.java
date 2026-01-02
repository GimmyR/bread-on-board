package mg.breadOnBoard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record StepsForm(
		@NotBlank(message = "Recipe ID is missing")
		String recipeId,
		
		Iterable<@Valid RecipeStepForm> steps
) {}