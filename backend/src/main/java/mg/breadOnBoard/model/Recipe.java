package mg.breadOnBoard.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mg.breadOnBoard.exception.FileIsEmptyException;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
	
	// ATTRIBUTES :
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_sequence_generator")
	@SequenceGenerator(name = "recipe_sequence_generator", sequenceName = "RECIPE_SEQ", allocationSize = 1)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Account account;
	
	private String title;
	
	private String image;
	
	@Column(length = 500)
	private String ingredients;
	
	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();
	
	// METHODS :
	
	public void editTitle(String title) {
		
		this.title = title;
		
	}
	
	public void editIngredients(String ingredients) {
		
		this.ingredients = ingredients;
		
	}
	
	public void editImage(MultipartFile image) throws FileIsEmptyException {
		
		if(image.isEmpty())
			throw new FileIsEmptyException();
		
		String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
		
		this.image = this.id + "." + extension;
		
	}

}
