package org.create.bankingApplication.transaction_service.service.implementation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.create.bankingApplication.transaction_service.exceptions.AccountStatusException;
import org.create.bankingApplication.transaction_service.exceptions.GlobalErrorCode;
import org.create.bankingApplication.transaction_service.exceptions.ResourceNotFoundException;
import org.create.bankingApplication.transaction_service.externalService.AccountService;
import org.create.bankingApplication.transaction_service.model.TransactionStatus;
import org.create.bankingApplication.transaction_service.model.TransactionType;
import org.create.bankingApplication.transaction_service.model.dto.TransactionServiceRequestDTO;
import org.create.bankingApplication.transaction_service.model.dto.external.AccountServiceDTO;
import org.create.bankingApplication.transaction_service.model.entity.Transaction;
import org.create.bankingApplication.transaction_service.model.mapper.TransactionServiceMapper;
import org.create.bankingApplication.transaction_service.model.responseDTO.ResponseDTO;
import org.create.bankingApplication.transaction_service.model.responseDTO.TransactionServiceResponseDTO;
import org.create.bankingApplication.transaction_service.repository.TransactionServiceRepository;
import org.create.bankingApplication.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService{
	
	private final TransactionServiceRepository transactionRepository;
	private final AccountService accountService;
	private final TransactionServiceMapper transactionMapper;
	
	
	@Value("${spring.application.ok}")
    private String ok;
	
	/*
    Adds a transaction
   
    param TransactionServiceRequestDTO The transaction to add
    throw ResourceNotFoundException if account doesent exist
    return The responseDTO indicating whether the transaction was successfully added
   */
	@Override
  public ResponseDTO addTransaction(TransactionServiceRequestDTO transactionServiceRequestDto) {
	  
	    //check whether account exists for given account Number for which transactions has to do
		ResponseEntity<AccountServiceDTO> response = accountService.readAccountByAccountNumber(transactionServiceRequestDto.getAccountNumber());
		
		if(Objects.isNull(response.getBody())) {         //if account doesen't exist
			throw new ResourceNotFoundException(GlobalErrorCode.NOT_FOUND, "Requested Account not found on the server");
		}
		
		//if account exists then
		AccountServiceDTO account = response.getBody();  
		//convert transaction dto to entity
		Transaction transaction = transactionMapper.toEntity(transactionServiceRequestDto);
		
		//now check tranaction type
		//if deposit      --deposits allowed to inactive accounts too
		if(transaction.getTransactionType() == TransactionType.DEPOSIT) { //enum comparison
			//add required amount into account
			//available balanace + amount requested 
			account.setAvailableBalance(account.getAvailableBalance().add(transactionServiceRequestDto.getAmount()));
			
		}else if(transaction.getTransactionType() == TransactionType.WITHDRAWAL) {  //enum comparison
			//if withdrawal       --withdrawal allowed for active accounts only
			//check account status for withdrawing amount
			if(!account.getAccountStatus().equals("ACTIVE")) {    //if account status is not active
				log.error("Account is not inactive or closed thus cannot process transaction");
				throw new AccountStatusException("Account is inactive or closed");
			}
			/*else withdraw amount from account
			available balanace - amount requested */
			
			/*negate method multiplies amount by -1 to make it -ve 
			so that we can easily track transactions made {withdraw=>-ve , deposit=>+ve}
			can check available balance easily without writing extra business logic*/
			transaction.setAmount(transactionServiceRequestDto.getAmount().negate());
			account.setAvailableBalance((account.getAvailableBalance()).subtract(transactionServiceRequestDto.getAmount()));
		}
		
		//now set some values for transaction entity after transaction done
		transaction.setAccountId(String.valueOf(account.getAccountId()));
		transaction.setTransactionType(TransactionType.valueOf(transactionServiceRequestDto.getTransactionType()));
		transaction.setTransactionStatus(TransactionStatus.COMPLETED);
		transaction.setComments(transactionServiceRequestDto.getDescription());
		transaction.setReferenceId(UUID.randomUUID().toString());
		
		
		//update account service
		accountService.updateAccount(account.getAccountNumber(), account);
		//save entity into respository
		transactionRepository.save(transaction);
		//return response dto for json format response
		//containing transaction status and code
		return ResponseDTO.builder()
				.responseCode(ok)
				.responseMessage("Transaction completed successfully")
				.build();		
		
  }

  /*
    Process an internal transaction => happens within same bank => account A-> account B ....no requirement of verification or less fees required
    [ handling multiple transactions in one go ]
    completes every transaction by changing its status, type, referenceid and saving them in repository
   
    param transactionRequestDTOs The list of transactionRequest DTOs to process {transactions dto of sender, receiever}
    param transactionReference The transaction reference id
    return The responseDTO of the internal transaction
   */
  @Override
  public ResponseDTO internalTransaction(List<TransactionServiceRequestDTO> transactionServiceRequestDtos, String referenceId) {
	  
		//convert list of transaction dtos into entity list
		List<Transaction> transactionEntityList = transactionMapper.toEntity(transactionServiceRequestDtos);
		
		//update transaction status,transaction type, referenceid of each transaction
		transactionEntityList.forEach(transaction ->
							{
								transaction.setTransactionStatus(TransactionStatus.COMPLETED);
								transaction.setTransactionType(TransactionType.INTERNAL_TRANSFER);
								transaction.setReferenceId(referenceId);								
							});
		
		transactionRepository.saveAll(transactionEntityList);
		return ResponseDTO.builder()
						.responseCode(ok)
						.responseMessage("Transaction done successfully")
						.build();
		
  }

  /*
    Retrieves a list of transactionRequestsDTOs for a given account Number
   
    param accountNumber
    throw ResourceNotFoundException if account not found 
    return a list of transactionRequestDTOs
   */
	@Override
  public List<TransactionServiceResponseDTO> getTransactionByAccountNumber(String accountNumber){
		
		return transactionMapper.toDTO(transactionRepository.findTransactionByAccountNumber(accountNumber));
		
  }

  /*
    Retrieves a list of transaction requests by transaction reference id 
   
    param transactionReference The transaction reference to search for 
    return A list of transaction requests matching the given transaction reference id
   */
	@Override
  public List<TransactionServiceResponseDTO> getTransactionByReferenceId(String referenceId){
		
		return transactionMapper.toDTO(transactionRepository.findTransactionByReferenceId(referenceId));
		
	}	
}
