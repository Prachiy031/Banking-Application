package org.create.bankingApplication.account_service.service.Implementation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.create.bankingApplication.account_service.exceptions.AccountClosingException;
import org.create.bankingApplication.account_service.exceptions.AccountStatusException;
import org.create.bankingApplication.account_service.exceptions.InsufficientFundsException;
import org.create.bankingApplication.account_service.exceptions.ResourceConflictException;
import org.create.bankingApplication.account_service.exceptions.ResourceNotFoundException;
import org.create.bankingApplication.account_service.externalService.SequenceService;
import org.create.bankingApplication.account_service.externalService.TransactionService;
import org.create.bankingApplication.account_service.externalService.UserService;
import org.create.bankingApplication.account_service.model.AccountType;
import org.create.bankingApplication.account_service.model.dto.AccountDTO;
import org.create.bankingApplication.account_service.model.dto.AccountStatusUpdateDTO;
import org.create.bankingApplication.account_service.model.dto.external.TransactionServiceResponseDTO;
import org.create.bankingApplication.account_service.model.dto.external.UserServiceDTO;
import org.create.bankingApplication.account_service.model.dto.mapper.AccountMapper;
import org.create.bankingApplication.account_service.model.dto.response.ResponseDTO;
import org.create.bankingApplication.account_service.model.entity.Account;
import org.create.bankingApplication.account_service.repository.AccountRepository;
import org.create.bankingApplication.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.create.bankingApplication.account_service.model.Constant.ACC_PREFIX;
import org.create.bankingApplication.account_service.model.AccountStatus;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImplementation implements AccountService{
	
	//business logic 
	//method implementation for methods declared in AccountService
	
	@Value("${spring.application.ok}")
    private String success;
	
	private final UserService userService;
	private final SequenceService sequenceService;
	private final TransactionService transactionService;
	private final AccountRepository accountRepository;
	private final AccountMapper accountMapper;
	/*
	 Create new Account 
	 return type ResponseDTO containing response code and message
	 param AccountDTO
	 */
	@Override
	public ResponseDTO createNewAccount(AccountDTO accountDto) {
		
		//retrieve userDTO using userId to check whether it exists or not
		ResponseEntity<UserServiceDTO> userServiceDto = userService.readUserById(accountDto.getUserId());
		if(Objects.isNull(userServiceDto.getBody())) {          //if response Entity's body is null
			throw new ResourceNotFoundException("User not found on the server");
		}
		
		//check if account already exists or not
		//by retrieving account using respective user id, account type
		//if present then return exception
		accountRepository.findAccountByUserIdAndAccountType(accountDto.getUserId(), AccountType.valueOf(accountDto.getAccountType()))
								.ifPresent(
										account ->{
											log.error("Account already exists");
											throw new ResourceConflictException("Account already exists on the server");
										});
		
		//convert given accountDto into entity
		Account account = accountMapper.toEntity(accountDto);
		//to customisely set values in entity..otherwise just by converting into entity will set values automatically in entity from dto values
		//here we are overriding previous set values 
		account.setAccountNumber(ACC_PREFIX + String.format("%07d",sequenceService.generateAccountNumber().getAccountNumber())); //add account prefix for accountnumber - total 7 digit account number , pad with 0 initially if less than 7 digit 
		account.setAccountStatus(AccountStatus.PENDING);      //initial account status pending
        account.setAvailableBalance(BigDecimal.valueOf(0));   //initial balanace 0        
        //save entity values
        accountRepository.save(account);
        
        //after creation of account in db return response
        return ResponseDTO.builder()
        		.code(success)
        		.message("Account created successfully")
        		.build();
		
	}
	
	
	/*
	 Retrieves Account Information using userId
	 return type AccountDto class' object
	 param userId
	 */
	@Override
	public AccountDTO readAccountByUserId(Long userId) {

		//check if account exists or not
		return accountRepository.findAccountByUserId(userId)
                .map(account ->{			//check each account
                    if(!account.getAccountStatus().equals(AccountStatus.ACTIVE)){    //if no active status of account then throw exception
                        throw new AccountStatusException("Account is inactive/closed");
                    }
                    //if active account then convert entity to dto
                    AccountDTO accountDto = accountMapper.toDto(account);  //everything will be mapped automatically from entity to dto {logic for mapping enum to string is mentioned in mapper}
                    return accountDto;
                }).orElseThrow(ResourceNotFoundException::new);        //if account not found
		
	}
	
	/*
	  Retrieves account information using accountNumber
	  return type AccountDTO class' obj
	  paarm accountNumber
	 */
	@Override
	public AccountDTO readAccountByAccountNumber(String accountNumber) {
		
		//check if account exists using account number
		return accountRepository.findAccountByAccountNumber(accountNumber) 
				.map(account ->{          //check each account
					if(AccountStatus.CLOSED.equals(account.getAccountStatus())) {    //check if account status is closed
						throw new AccountStatusException("Account is already closed");
					}
					
					//if active/pending status then
					//convert entity to dto
					AccountDTO accountDto = accountMapper.toDto(account); //everything will be mapped automatically from entity to dto {logic for mapping enum to string is mentioned in mapper}
					return accountDto;
				}).orElseThrow(ResourceNotFoundException::new);        //if account not found
	}
	
	
	/*
	 retrieves balance of account with specified account Number
	 return type acountBalance {string}
	 parameter accountNumber
	 */
	@Override
	public String getAccountBalance(String accountNumber) {
		//check account exists with given accountNumber
		return accountRepository.findAccountByAccountNumber(accountNumber)
                				.map(account -> account.getAvailableBalance().toString())  //for that account retrieve available balanace
                				.orElseThrow(ResourceNotFoundException::new); //if account with given account number not found
	}
	
	/*
    Retrieves a list of transaction responses from the specified accountNumber
    return list of TransactionServiceResponseDTO 
    param accountNumber 
   */
	@Override
	public List<TransactionServiceResponseDTO> getTransactions(String accountNumber) {
		
		return transactionService.getTransactionsByAccountNumber(accountNumber);
		
	}
	
	/*
    Update Account Status using accountNumber and AccountStatus mentioned
    return type responseDTO containing response code and message
    param accountNumber and AccountStatus to update
    */
	@Override
	public ResponseDTO updateAccountStatus(String accountNumber, AccountStatusUpdateDTO accountStatusUpdateDto) {
		
		return accountRepository.findAccountByAccountNumber(accountNumber)     //find account by given account number
                .map(account -> {                      //for respective account
                    if(account.getAccountStatus().equals(AccountStatus.CLOSED)){    //check status of account
                        throw new AccountStatusException("Cannot update status of permanently closed account");      //if not active
                    }
                    if(accountStatusUpdateDto.getAccountStatus().equals(AccountStatus.ACTIVE) && (account.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0 || account.getAvailableBalance().compareTo(BigDecimal.valueOf(1000)) < 0)){ //if not sufficient balance
                        throw new InsufficientFundsException("Minimum balance of Rs.1000 is required");
                    }
                    account.setAccountStatus(accountStatusUpdateDto.getAccountStatus());    //set account status in entity class
                    accountRepository.save(account);      //save in db
                    //updated successfully
                    return ResponseDTO.builder()
                    				.message("Account Status updated successfully")
                    				.code(success)
                    				.build();
                }).orElseThrow(() -> new ResourceNotFoundException("Account not on the server")); //if account not found then throw exception
	}
	
	
	/*
	 Update account info with specified account number
	 return type responseDTO
	 param accountNumber and AccountDTO class obj 
	 */
	@Override
	public ResponseDTO updateAccount(String accountNumber, AccountDTO accountDto) {
		//find out account exists or not for given acc number
		return accountRepository.findAccountByAccountNumber(accountNumber)
	            .map(account -> {      //for that account 
	                accountMapper.updateEntityFromDto(accountDto, account); //update entity from dto fields
	                accountRepository.save(account);   //save entity in db
	                
	                //return response
	                return ResponseDTO.builder()
	                        .code(success)
	                        .message("Account updated successfully")
	                        .build();
	            }).orElseThrow(() -> new ResourceNotFoundException("Account not found on the server")); //if account not found
	}
	
	
	/*
    Closes account by account number
     return type ResponseDTO class' obj
     param accountNumber
    */
	@Override
	@Transactional        //automatic commit
	public ResponseDTO closeAccount(String accountNumber) {
		
		return accountRepository.findAccountByAccountNumber(accountNumber) //find account using account number
                .map(account -> {     //for that account
                	
                	//check if account balance is 0 or not to close account
                    if(BigDecimal.valueOf(Double.parseDouble(getAccountBalance(accountNumber))).compareTo(BigDecimal.ZERO) != 0) { 
                        throw new AccountClosingException("Balance should be zero");
                    }
                    account.setAccountStatus(AccountStatus.CLOSED); //set account status
                    //return response
                    return ResponseDTO.builder()
                            		.message("Account closed successfully")
                            		.code(success)
                            		.build();
                }).orElseThrow(ResourceNotFoundException::new); //if account not found
		
	}
	
}
