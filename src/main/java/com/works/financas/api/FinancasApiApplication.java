package com.works.financas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.works.financas.api.config.property.FinancasApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(FinancasApiProperty.class)
@EnableJpaAuditing 
public class FinancasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancasApiApplication.class, args);
	}
}
