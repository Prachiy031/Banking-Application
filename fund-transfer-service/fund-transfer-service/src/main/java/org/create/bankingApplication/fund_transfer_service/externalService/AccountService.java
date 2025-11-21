package org.create.bankingApplication.fund_transfer_service.externalService;

import org.create.bankingApplication.fund_transfer_service.model.dto.external.AccountServiceDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountService {
	
	/*
	  Retrieves account information using accountNumber
	  return type AccountDTO class' obj
	  paarm accountNumber
	 */
	@GetMapping("/accounts")
	ResponseEntity<AccountServiceDTO> readAccountByAccountNumber(@RequestParam String accountNumber);
	
	/*
	 Update account info with specified account number
	 returh type responseDTO
	 param accountNumber and AccountDTO class obj 
	 */
	@PutMapping("/accounts")
   ResponseEntity<ResponseDTO> updateAccount(@RequestParam String accountNumber, @RequestBody AccountServiceDTO accountDto);
	
}
