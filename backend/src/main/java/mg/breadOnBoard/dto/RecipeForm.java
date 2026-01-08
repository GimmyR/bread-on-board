package mg.breadOnBoard.dto;

import jakarta.validation.constraints.NotBlank;

public record RecipeForm(
		@NotBlank(message = "Title is missing")
		String title,
		
		@NotBlank(message = "Ingredients are missing")
		String ingredients
) {}
