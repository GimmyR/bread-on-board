package mg.breadOnBoard.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mg.breadOnBoard.exception.FileIsEmptyException;
import mg.breadOnBoard.model.Recipe;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.EncryptionTypeMismatchException;
import software.amazon.awssdk.services.s3.model.InvalidRequestException;
import software.amazon.awssdk.services.s3.model.InvalidWriteOffsetException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.TooManyPartsException;

@Service
public class ImageService {
	
	@Autowired(required = false)
	private S3Client s3Client;
	private final Path uploadsDir = Paths.get("images");
	
	private Recipe uploadOnLocal(Recipe recipe, MultipartFile image) throws IOException, FileIsEmptyException {
		
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
	
	private Recipe uploadOnSupabase(Recipe recipe, MultipartFile image) throws FileIsEmptyException, InvalidRequestException, InvalidWriteOffsetException, TooManyPartsException, EncryptionTypeMismatchException, S3Exception, AwsServiceException, SdkClientException, IOException {
		
		recipe.editImage(image);
		PutObjectRequest request = PutObjectRequest
										.builder()
										.bucket("uploads")
										.key(recipe.getImage())
										.contentType(image.getContentType())
										.build();
		
		s3Client.putObject(request, RequestBody.fromBytes(image.getBytes()));
		
		return recipe;
		
	}
	
	public Recipe upload(Recipe recipe, MultipartFile image) throws FileIsEmptyException, IOException {
		
		if(s3Client == null)
			return this.uploadOnLocal(recipe, image);
		
		else return this.uploadOnSupabase(recipe, image);
		
	}
	
	public void delete(String filename) throws IOException {
		
		if(filename != null) {
		
			Path file = uploadsDir.resolve(filename);
			
			if(Files.exists(file))
				Files.delete(file);
		
		}
		
	}

}
