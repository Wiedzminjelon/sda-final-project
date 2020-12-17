package socialmediaapp.twitterinspiredapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import socialmediaapp.twitterinspiredapp.config.SwaggerConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@Import(SwaggerConfiguration.class)
@EnableAsync
public class TwitterInspiredAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterInspiredAppApplication.class, args);
	}

}
