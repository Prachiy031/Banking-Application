package org.create.bankingApplication.apigateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;


/*
 API gateway used to centralize API endpoint => All endpoints
 				have single entry point to all other endpoints
 Also provides security inclusion => authorization and authentication
 */
@SpringBootApplication
//@ComponentScan(basePackages = "org.create.bankingApplication") 
public class ApigatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayServiceApplication.class, args);
	}

}
