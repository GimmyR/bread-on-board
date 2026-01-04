package mg.breadOnBoard.dto;

public record RecipeResponse(
	String id,
	String title,
	String ingredients,
	String image,
	AccountResponse account
) {}
