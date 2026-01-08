package mg.breadOnBoard.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import mg.breadOnBoard.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Value("${JWT_SECRET}")
	private String secretKey;
	
	@Value("${FRONTEND_URL}")
	private String frontendURL;
	
	@Value("${PASSWORD_STRENGTH}")
	private Integer passwordStrength;
	
	@Bean
	SecurityFilterChain apiSecurity(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
				.securityMatcher("/api/**")
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.csrf(csrf -> csrf.disable())
    			.cors(Customizer.withDefaults())
    			.authorizeHttpRequests(authorize -> authorize.requestMatchers(
    					"/api/recipe/create", 
    					"/api/recipe/edit/**",
    					"/api/recipe/delete/**",
    					"/api/recipe/author/**",
    					"/api/recipe-step/save-all",
    					"/api/account/username",
    					"/api/my-recipes").authenticated())
				.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
				.userDetailsService(userDetailsService)
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
		
	}
	
	@Bean
    SecurityFilterChain webSecurity(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
				.formLogin(login -> login.loginPage("/sign-in").defaultSuccessUrl("/").permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/sign-in?logout").permitAll())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/webjars/**", "/css/**", "/images/**").permitAll())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/actuator/**").permitAll())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").hasRole("Admin"))
				.userDetailsService(userDetailsService);
		
		return httpSecurity.build();
		
	}
	
	
	@Bean
	JwtEncoder jwtEncoder() {
		
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
		
	}
	
	@Bean
	JwtDecoder jwtDecoder() {
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
		
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
		
	}
	
	@Bean
	AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		
		return new ProviderManager(daoAuthenticationProvider);
		
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin(frontendURL);
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addExposedHeader("X-Message");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		
		return source;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(passwordStrength);
		
	}

}
