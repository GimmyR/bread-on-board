package mg.breadOnBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mg.breadOnBoard.exception.FileIsEmptyException;
import mg.breadOnBoard.model.Recipe;

@Service
public class ImageService {
	
	private final Path uploadsDir = Paths.get("images");
	
	public Recipe upload(Recipe recipe, MultipartFile image) throws IOException, FileIsEmptyException {
		
		if(!Files.exists(uploadsDir) || !Files.isDirectory(uploadsDir))
			Files.createDirectories(uploadsDir);
		
		String oldImage = recipe.getImage();
		recipe.editImage(image);
		Files.copy(
				image.getInputStream(), 
				uploadsDir.resolve(recipe.getImage()),
				StandardCopyOption.REPLACE_EXISTING
		);
		
		if(oldImage != null && !oldImage.equals(recipe.getImage()))
			this.delete(oldImage);
		
		return recipe;
		
	}
	
	public void delete(String filename) throws IOException {
		
		if(filename != null) {
		
			Path file = uploadsDir.resolve(filename);
			
			if(Files.exists(file))
				Files.delete(file);
		
		}
		
	}

}
