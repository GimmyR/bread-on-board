package mg.breadOnBoard.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class OracleSchemaInitializer {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initSequences() {
    	
        jdbcTemplate.execute("""
            BEGIN
			  EXECUTE IMMEDIATE 'CREATE SEQUENCE ACCOUNT_SEQ START WITH 2 INCREMENT BY 1 NOORDER';
			EXCEPTION
			  WHEN OTHERS THEN
			    IF SQLCODE != -955 THEN
			      RAISE;
			    END IF;
			END;
		""");
        
        jdbcTemplate.execute("""
			BEGIN
			  EXECUTE IMMEDIATE 'CREATE SEQUENCE RECIPE_SEQ START WITH 1 INCREMENT BY 1 NOORDER';
			EXCEPTION
			  WHEN OTHERS THEN
			    IF SQLCODE != -955 THEN
			      RAISE;
			    END IF;
			END;
		""");
        
		jdbcTemplate.execute("""
			BEGIN
			  EXECUTE IMMEDIATE 'CREATE SEQUENCE RECIPE_STEP_SEQ START WITH 1 INCREMENT BY 1 NOORDER';
			EXCEPTION
			  WHEN OTHERS THEN
			    IF SQLCODE != -955 THEN
			      RAISE;
			    END IF;
			END;
		""");
        
    }
}

