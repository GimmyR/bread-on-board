package mg.breadOnBoard.dto;

import jakarta.validation.constraints.NotBlank;

public record RecipeStepForm(
		@NotBlank(message = "A step for your recipe is missing")
		String text
) {}
