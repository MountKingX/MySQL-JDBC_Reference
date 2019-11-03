package com.kangmin.flyway.config;


import com.kangmin.flyway.model.Person;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Import({DatabaseConfig.class})
@Configuration
public class AppConfig {

	@Bean(initMethod = "migrate")
	public Flyway flyway(final DataSource dataSource) {
		final Flyway flyway = Flyway.configure()
				.baselineOnMigrate(true)
				.dataSource(dataSource)
				.locations("filesystem:src/main/resources/db/migration/")
				.encoding("UTF-8")
				.load();
		flyway.clean();
		System.out.println("cleaned");
		flyway.migrate();
		return flyway;
	}

	@Bean
	public Person myPerson() {
		return new Person("testP", 11);
	}
}
