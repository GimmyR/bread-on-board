package mg.breadOnBoard.dto;

public record RecipeResponse(
	Long id,
	String title,
	String ingredients,
	String image,
	AccountResponse account,
	Iterable<RecipeStepResponse> steps
) {}
