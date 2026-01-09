package mg.breadOnBoard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI openApi() {
		
		Info info = new Info().title("Documentation for Bread on Board API");
		
		return new OpenAPI()
					.info(info)
					.components(new Components()
		                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
		                        .type(SecurityScheme.Type.HTTP)
		                        .scheme("bearer")
		                        .bearerFormat("JWT") // Specify JWT format if applicable
		                        .name("Authorization")
		                        .in(SecurityScheme.In.HEADER)))
		                	.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
		
	}

}
