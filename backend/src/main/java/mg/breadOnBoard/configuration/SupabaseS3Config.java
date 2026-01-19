package mg.breadOnBoard.configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class SupabaseS3Config {
	
	@Value("${SUPABASE_S3_ENDPOINT:#{null}}")
	private String endpoint;
	
	@Value("${SUPABASE_S3_ACCESS_KEY:#{null}}")
	private String accessKey;
	
	@Value("${SUPABASE_S3_SECRET_KEY:#{null}}")
	private String secretKey;
	
	@Bean
	@ConditionalOnProperty(name = { "SUPABASE_S3_ENDPOINT", "SUPABASE_S3_ACCESS_KEY", "SUPABASE_S3_SECRET_KEY" })
	public S3Client supabaseS3Client() {
		
		return S3Client.builder()
						.endpointOverride(URI.create(endpoint))
						.region(Region.EU_CENTRAL_1)
						.credentialsProvider(
								StaticCredentialsProvider.create(
										AwsBasicCredentials.create(accessKey, secretKey)
								)
						).serviceConfiguration(
								S3Configuration.builder()
												.pathStyleAccessEnabled(true)
												.build()
						).build();
		
	}

}
