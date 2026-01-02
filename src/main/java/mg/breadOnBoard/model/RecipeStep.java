package mg.breadOnBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_step")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeStep {
	
	public static final String PREFIX = "S";
	
	public static final int LENGTH_ID = 6;
	
	// ATTRIBUTES :
	
	@Id
	private String id;
	
	@Column(name = "recipe_id")
	private String recipeId;
	
	@Column(name = "step_order")
	private int order;
	
	private String text;
	
	// METHODS :
	
	public void editId(String id) {
		
		this.id = id;
		
	}

	public void editRecipeId(String recipeId) {
		
		this.recipeId = recipeId;
		
	}

	public void editOrder(int order) {
		
		this.order = order;
		
	}
	
	

}
