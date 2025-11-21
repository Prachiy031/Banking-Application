package org.create.bankingApplication.apigateway_service;

import org.springframework.boot.SpringApplication;

public class TestApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ApigatewayServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
