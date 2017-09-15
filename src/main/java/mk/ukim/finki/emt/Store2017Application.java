package mk.ukim.finki.emt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableOAuth2Client
@SpringBootApplication
public class Store2017Application extends WebMvcAutoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(Store2017Application.class, args);
	}
}
