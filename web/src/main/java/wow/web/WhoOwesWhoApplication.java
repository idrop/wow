package wow.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WhoOwesWhoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhoOwesWhoApplication.class, args);
	}

}
