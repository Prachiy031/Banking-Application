package org.create.bankingApplication.user_service.externalService;

import org.create.bankingApplication.user_service.model.external.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*Feign is like a shortcut for calling another microservice’s API.
Instead of writing long RestTemplate or WebClient code, 
you just write a Java interface, and Feign will automatically 
create the HTTP request behind the scenes*/
@FeignClient(name="account-service")
public interface AccountService {
	
	/*
	 Retrieves account by its account id 
	 return type responseEntity
	 parameter account id
	 */
	@GetMapping("/accounts")
	ResponseEntity<Account> readByAccountNumber(@RequestParam String accountNumber);
	
}
