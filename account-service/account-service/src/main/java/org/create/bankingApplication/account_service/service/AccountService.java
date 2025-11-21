package org.create.bankingApplication.account_service.service;

import java.util.List;

import org.create.bankingApplication.account_service.model.dto.AccountDTO;
import org.create.bankingApplication.account_service.model.dto.AccountStatusUpdateDTO;
import org.create.bankingApplication.account_service.model.dto.external.TransactionServiceResponseDTO;
import org.create.bankingApplication.account_service.model.dto.response.ResponseDTO;

public interface AccountService {
	
	
	//crud operations method declaration
	
	/*
	 Create new Account 
	 return type ResponseDTO containing response code and message
	 param AccountDTO
	 */
	ResponseDTO createNewAccount(AccountDTO accountDto);
	
	/*
	 Retrieves Account Information using userId
	 return type AccountDto class' object
	 param userId
	 */
	AccountDTO readAccountByUserId(Long userId);
	
	
	/*
	  Retrieves account information using accountNumber
	  return type AccountDTO class' obj
	  paarm accountNumber
	 */
	AccountDTO readAccountByAccountNumber(String accountNumber);
	
	
	/*
	 retrieves balance of account with specified account Number
	 return type acountBalance {string}
	 parameter accountNumber
	 */
	String getAccountBalance(String accountNumber);
	
	
	/*
      Retrieves a list of transaction responses from the specified account Number
      return list of TransactionServiceResponseDTO 
      param accountId 
     */
    List<TransactionServiceResponseDTO> getTransactions(String accountNumber);
    
    /*
     Update Account Status using accountNumber and AccountStatus mentioned
     return type responseDTO containing response code and message
     param accountNumber and AccountStatus to update
     */
    ResponseDTO updateAccountStatus(String accountNumber, AccountStatusUpdateDTO accountStatusUpdateDto);
    
	/*
	 Update account info with specified account number
	 returh type responseDTO
	 param accountNumber and AccountDTO class obj 
	 */
    ResponseDTO updateAccount(String accountNumber, AccountDTO accountDto);
    
    
    /*
     Closes account by account number
      return type ResponseDTO class' obj
      param accountNumber
     */
    ResponseDTO closeAccount(String accountNumber);
    
    
	
}
