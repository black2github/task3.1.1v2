package ru.kata.CRUD_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories("ru.kata.CRUD_spring_boot.model") // to scan the specified package for repositories
//@EntityScan("ru.kata.CRUD_spring_boot.model") // to pick up our JPA entities
@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
