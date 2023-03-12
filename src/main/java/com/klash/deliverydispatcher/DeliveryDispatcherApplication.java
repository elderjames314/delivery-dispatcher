package com.klash.deliverydispatcher;


import com.klash.deliverydispatcher.auth.AuthenticationService;
import com.klash.deliverydispatcher.auth.RegisterRequest;
import com.klash.deliverydispatcher.location.Location;
import com.klash.deliverydispatcher.location.LocationRequest;
import com.klash.deliverydispatcher.location.LocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeliveryDispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryDispatcherApplication.class, args);
	}


	@Bean
	CommandLineRunner init(LocationService locationService) {

		return args -> {
			locationService.save(LocationRequest.builder().lat("6.458985").lon("3.601521").name("Oshodi").build());
			locationService.save(LocationRequest.builder().lat("6.458985").lon("3.601521").name("Lekki").build());
			locationService.save(LocationRequest.builder().lat("6.466667").lon("3.566667").name("Ajah").build());
		};
	}

	@Bean
	CommandLineRunner init2(AuthenticationService authenticationService) {

		return args -> {
			authenticationService.register(RegisterRequest.builder().name("admin").password("123456778").email("emmajoseph@yopmail.com").build());
		};
	}

}
