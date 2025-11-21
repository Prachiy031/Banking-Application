package org.create.bankingApplication.service_registry_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;



/*
 a central, highly available directory 
 used in distributed systems, particularly microservices, 
 to store and provide information about the location 
 and availability of services.
 
 discovery service used for service registration and service discovery,
 helps other services to communicate with each other without hardcoding endpoints
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryServiceApplication.class, args);
	}

}
