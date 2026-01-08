package mg.breadOnBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
	
	// ATTRIBUTES :
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_step_sequence_generator")
	@SequenceGenerator(name = "recipe_step_sequence_generator", sequenceName = "RECIPE_STEP_SEQ", allocationSize = 1)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Recipe recipe;
	
	@Column(name = "step_order")
	private int order;
	
	private String text;
	
	// METHODS :
	
	public void editId(Long id) {
		
		this.id = id;
		
	}

	public void editRecipeId(Recipe recipe) {
		
		this.recipe = recipe;
		
	}

	public void editOrder(int order) {
		
		this.order = order;
		
	}
	
	

}
