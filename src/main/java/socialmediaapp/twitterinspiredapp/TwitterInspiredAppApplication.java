package socialmediaapp.twitterinspiredapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import socialmediaapp.twitterinspiredapp.config.SwaggerConfiguration;


@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class TwitterInspiredAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterInspiredAppApplication.class, args);
	}

}
