package mg.breadOnBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mg.breadOnBoard.exception.FileIsEmptyException;

@Service
public class ImageService {
	
	private final Path uploadsDir = Paths.get("images");
	
	public String upload(MultipartFile file) throws FileIsEmptyException, IOException {
		
		if(file.isEmpty())
			throw new FileIsEmptyException();
			
		Files.copy(file.getInputStream(), uploadsDir.resolve(file.getOriginalFilename()));
		
		return file.getOriginalFilename();
		
	}
	
	public void delete(String filename) throws IOException {
		
		Path file = uploadsDir.resolve(filename);
		Files.delete(file);
		
	}

}
