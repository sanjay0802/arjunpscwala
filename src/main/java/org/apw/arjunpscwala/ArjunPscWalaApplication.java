package org.apw.arjunpscwala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ArjunPscWalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArjunPscWalaApplication.class, args);
	}

}
