package com.eugene.android_rest;

import com.eugene.android_rest.database.services.interfaces.UserService;
import com.eugene.android_rest.domain.User;
import com.eugene.android_rest.elasticsearch.services.interfaces.ElasticService;
import com.eugene.android_rest.faker.FakerBean;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.eugene.android_rest.database.repository")
@EnableElasticsearchRepositories(basePackages = "com.eugene.android_rest.elasticsearch.repository")
@Slf4j
public class AndroidRestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AndroidRestApplication.class, args);

		FakerBean fakerBean = context.getBean(FakerBean.class);
		UserService service = context.getBean(UserService.class);
		ElasticService elasticService = context.getBean(ElasticService.class);

		Faker faker = fakerBean.getFaker();

		for (int i = 0; i < 100; i++) {
			User user = User.builder()
					.firstName(faker.name().firstName())
					.lastName(faker.name().lastName())
					.build();

			service.create(user);
			log.info("Created: {}", user.toString());
		}

		elasticService.refresh();
	}
}
