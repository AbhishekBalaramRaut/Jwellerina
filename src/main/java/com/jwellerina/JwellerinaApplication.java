package com.jwellerina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.jwellerina"})
@EntityScan({"com.jwellerina"})
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class JwellerinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwellerinaApplication.class, args);
	}

}
