package org.create.bankingApplication.account_service.controller;

import java.util.List;

import org.create.bankingApplication.account_service.model.dto.AccountDTO;
import org.create.bankingApplication.account_service.model.dto.AccountStatusUpdateDTO;
import org.create.bankingApplication.account_service.model.dto.external.TransactionServiceResponseDTO;
import org.create.bankingApplication.account_service.model.dto.response.ResponseDTO;
import org.create.bankingApplication.account_service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController      //Controller + ResponseBody
@RequestMapping("/accounts")   //base url for controller methods
@RequiredArgsConstructor         //for initialiseing final fields
public class AccountController {
	
	private final AccountService accountService;
	/*
	 Create new Account 
	 return type response entity of ResponseDTO containing response code and message
	 param AccountDTO
	 */
	@PostMapping
	public ResponseEntity<ResponseDTO> createNewAccount(@RequestBody AccountDTO accountDto) {
		//new instance of Response Entity is created when status code is customised 
		//this will sets status code as mentioned, sets body to whatever passed
		//we can use direct method too like ResponseEntity.ok(body)
		return new ResponseEntity<>(accountService.createNewAccount(accountDto),HttpStatus.CREATED);
	}
	
	/*
	 Retrieves Account Information using userId
	 return type AccountDto class' object
	 param userId
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<AccountDTO> readAccountByUserId(@PathVariable Long userId){
		//this will automatically create instance of responseentity with status code as mentioned 
		//no customised status code
		//this will automatically create status code, sets body to whetver you have passed in
		return ResponseEntity.ok(accountService.readAccountByUserId(userId));
	}
	
	
	/*
	  Retrieves account information using accountNumber
	  return type AccountDTO class' obj
	  param accountNumber
	 */
	@GetMapping
	public ResponseEntity<AccountDTO> readAccountByAccountNumber(@RequestParam String accountNumber) {
		return ResponseEntity.ok(accountService.readAccountByAccountNumber(accountNumber));
	}
	
	
	/*
	 retrieves balance of account with specified account Number
	 return type acountBalance {string}
	 parameter accountNumber
	 */
	@GetMapping("/balance")
	public ResponseEntity<String> getAccountBalance(@RequestParam String accountNumber) {
		return ResponseEntity.ok(accountService.getAccountBalance(accountNumber));
	}
	
	
	/*
     Retrieves a list of transaction responses from the specified account ID
     return list of TransactionServiceResponseDTO 
     param accountId 
    */
	@GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<TransactionServiceResponseDTO>> getTransactions(@PathVariable String accountNumber){
		return ResponseEntity.ok(accountService.getTransactions(accountNumber));
	}
   
   /*
    Update Account Status using accountNumber and AccountStatus mentioned
    return type responseDTO containing response code and message
    param accountNumber and AccountStatus to update
    */
	@PatchMapping
   public ResponseEntity<ResponseDTO> updateAccountStatus(@RequestParam String accountNumber, @RequestBody AccountStatusUpdateDTO accountStatusUpdateDto){
		return ResponseEntity.ok(accountService.updateAccountStatus(accountNumber, accountStatusUpdateDto));
	}
   
	/*
	 Update account info with specified account number
	 return type responseDTO
	 param accountNumber and AccountDTO class obj 
	 */
	@PutMapping
   public ResponseEntity<ResponseDTO> updateAccount(@RequestParam String accountNumber, @RequestBody AccountDTO accountDto){
		return ResponseEntity.ok(accountService.updateAccount(accountNumber, accountDto));
	}
   
   
   /*
    Closes account by account number
     return type ResponseDTO class' obj
     param accountNumber
    */
	@PutMapping("/closure")
	public ResponseEntity<ResponseDTO> closeAccount(@RequestParam String accountNumber){
		return ResponseEntity.ok(accountService.closeAccount(accountNumber));
	}
   
}
