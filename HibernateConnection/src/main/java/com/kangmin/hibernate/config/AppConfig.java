package com.kangmin.hibernate.config;

import com.kangmin.hibernate.model.Person;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(
		DaoConfig.class
)
@Configuration
@ComponentScan(basePackages="com.kangmin.hibernate")
public class AppConfig {

	@Bean
	public Person myPerson() {
		return new Person("TestName0", 10);
	}

}
