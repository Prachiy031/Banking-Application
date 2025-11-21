package org.create.bankingApplication.transaction_service.externalService;

import org.create.bankingApplication.transaction_service.model.dto.external.AccountServiceDTO;
import org.create.bankingApplication.transaction_service.model.responseDTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service") //to connect current service to account service
public interface AccountService {
	
	/*
	  Retrieves account information using accountNumber
	  return type AccountDTO class' obj response entity
	  paarm accountNumber
	 */
	@GetMapping("/accounts")
	ResponseEntity<AccountServiceDTO> readAccountByAccountNumber(@RequestParam String accountNumber);
	
	/*
	 Update account info with specified account number
	 return type responseDTO respose entity
	 param accountNumber and AccountDTO class obj 
	 */
	@PutMapping("/accounts")
   ResponseEntity<ResponseDTO> updateAccount(@RequestParam String accountNumber, @RequestBody AccountServiceDTO accountDto);
}
