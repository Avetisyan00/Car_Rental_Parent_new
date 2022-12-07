package am.itspace.car_rental_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"am.itspace.car_rental_web.*", "am.itspace.car_rental_common.*"})
@EntityScan(basePackages = "am.itspace.car_rental_common.*")
@EnableJpaRepositories(basePackages = "am.itspace.car_rental_common.*")

public class CarRentalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalWebApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

