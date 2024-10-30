package Refact.CalvinRefact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CalvinRefactApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalvinRefactApplication.class, args);
	}

}
