package mg.breadOnBoard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record StepsForm(
		@NotNull(message = "Recipe ID is missing")
		Long recipeId,
		
		Iterable<@Valid RecipeStepForm> steps
) {}