package com.amachlou.search_app_back;

import com.amachlou.search_app_back.entities.Product;
import com.amachlou.search_app_back.repositories.jpa.ProductRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableJpaAuditing
public class SearchAppBackApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(SearchAppBackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productRepository.save(getRandomProduct());
	}

	public static Product getRandomProduct() {
		Faker faker = new Faker();
		String name = faker.commerce().productName();

		String description = name+" "+faker.commerce().productName();
		String price = faker.commerce().price();

		return new Product(null, name, description, Double.parseDouble(price));
	}
}
