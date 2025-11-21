package org.create.bankingApplication.fund_transfer_service.service.implementation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.create.bankingApplication.fund_transfer_service.exceptions.AccountUpdateException;
import org.create.bankingApplication.fund_transfer_service.exceptions.GlobalErrorCode;
import org.create.bankingApplication.fund_transfer_service.exceptions.InsufficientBalanceException;
import org.create.bankingApplication.fund_transfer_service.exceptions.ResourceNotFoundException;
import org.create.bankingApplication.fund_transfer_service.externalService.AccountService;
import org.create.bankingApplication.fund_transfer_service.externalService.TransactionService;
import org.create.bankingApplication.fund_transfer_service.model.TransactionStatus;
import org.create.bankingApplication.fund_transfer_service.model.TransferType;
import org.create.bankingApplication.fund_transfer_service.model.dto.FundTransferDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.external.AccountServiceDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.external.TransactionServiceDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.mapper.FundTransferMapper;
import org.create.bankingApplication.fund_transfer_service.model.dto.requestDTO.FundTransferRequestDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO.FundTransferResponseDTO;
import org.create.bankingApplication.fund_transfer_service.model.entity.FundTransfer;
import org.create.bankingApplication.fund_transfer_service.repository.FundTransferRepository;
import org.create.bankingApplication.fund_transfer_service.service.FundTransferService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FundTransferServiceImplementation implements FundTransferService{
	
	private final AccountService accountService;
	private final TransactionService transactionService;
	private final FundTransferMapper fundTransferMapper;
	private final FundTransferRepository fundTransferRepository;
	
	@Value("${spring.application.ok}")
    private String ok;
	
	/*
	 transfers fund from one account to another
	 param fundTranferRequestDTO
	 return type FundTransferResponseDTo 
	 */
	
	@Transactional               //indicates that either whole transaction will commit or roll back
	@Override
	public FundTransferResponseDTO fundTransfer(FundTransferRequestDTO fundTranferRequestDto) {
		
		
		/*
		 Initially FundTransfer entity object is created to 
		 set trasanctionstatus in phases => pending, processing, success, failed, cancelled  
		 */
		FundTransfer fundTransfer = FundTransfer.builder()
											.transferType(TransferType.INTERNAL)
											.transactionStatus(TransactionStatus.PENDING)      //Initial fund transfer status is pending 
											.fromAccount(fundTranferRequestDto.getFromAccount())
											.toAccount(fundTranferRequestDto.getToAccount())
											.amount(fundTranferRequestDto.getAmount())
											.build();
		
		fundTransferRepository.save(fundTransfer);
		
		
		try {		
			/*
			 Set transaction status as processing 
			 */
			fundTransfer.setTransactionStatus(TransactionStatus.PROCESSING);
			fundTransferRepository.save(fundTransfer);
		
			//check whether senders's account exists with help of sender's account number
			ResponseEntity<AccountServiceDTO> response = accountService.readAccountByAccountNumber(fundTranferRequestDto.getFromAccount());
			
				if(Objects.isNull(response.getBody())) {
					log.error("Requested Account "+fundTranferRequestDto.getFromAccount()+" does not found on the server");
					throw new ResourceNotFoundException(GlobalErrorCode.NOT_FOUND, "Requested Account not found on the server");
				}
			
			//if account found
			AccountServiceDTO fromAccountDto = response.getBody();
			//check account status whether its active or not
				if(!fromAccountDto.getAccountStatus().equals("ACTIVE")) {
					//if not active
					log.error("Account status is pending or inactive, please update the account");
					throw new AccountUpdateException(GlobalErrorCode.NOT_ACCEPTABLE, "Account status is "+fromAccountDto.getAccountStatus());
				}
			
				/*if account is active 
				then check whether available balance is less than amount to be transfer*/
				if(fromAccountDto.getAvailableBalance().compareTo(fundTranferRequestDto.getAmount()) < 0) {
					log.error("Required amount to transfer is not available");
					throw new InsufficientBalanceException(GlobalErrorCode.NOT_ACCEPTABLE, "Requested amount is not available in account");
				}
		
			//check whether receiver's account exists with help of receiver's account number
			response = accountService.readAccountByAccountNumber(fundTranferRequestDto.getToAccount());
			
				if(Objects.isNull(response.getBody())) {
					log.error("Requested Account "+fundTranferRequestDto.getFromAccount()+" does not found on the server");
					throw new ResourceNotFoundException(GlobalErrorCode.NOT_FOUND, "Requested Account not found on the server");
				}
			
			//if account found
			AccountServiceDTO toAccountDto = response.getBody();
		
				//check account status whether its active or not
				if(!toAccountDto.getAccountStatus().equals("ACTIVE")) {
					//if not active
					log.error("Account status is pending or inactive, please update the account");
					throw new AccountUpdateException(GlobalErrorCode.NOT_ACCEPTABLE, "Account status is "+toAccountDto.getAccountStatus());
				}
		
			//Start transaction and get transaction reference id back
			String transactionId = internalTransfer(fromAccountDto, toAccountDto, fundTranferRequestDto.getAmount());
			
			/*
			 Set transaction status as Success after completion of transaction
			 */
			fundTransfer.setTransactionStatus(TransactionStatus.SUCCESS);
			fundTransfer.setTransactionReferenceId(transactionId);
			/*when entity is persited(save) in db by hibernate, on that time transferredOn is set 
			so that's why not set here during setting entity*/
			fundTransferRepository.save(fundTransfer);
			
			//return fundTransfer Response DTO
			return FundTransferResponseDTO.builder()
										.transactionReferenceId(transactionId)
										.transactionMessage("Fund Transfer has successfully done")
										.build();
			
			}catch(Exception exception) {
				/*if transaction has failed
				then set transaction status as failed*/
				fundTransfer.setTransactionStatus(TransactionStatus.FAILED);
				fundTransferRepository.save(fundTransfer);
				
				throw exception;           //exception is thown for controller
			}
	}
	
	/*
	 Internal transfer method (helper method for fund transfer method) => from one account to another  
	 param 	fromAccountDTO account to transfer funds from, 
	 		toAccountDTO account to receive funds,  
	 		amount to trasfer from sender's accoun to receiver's account
	 return transactionReferenceId
	 */
	private String internalTransfer(AccountServiceDTO fromAccountDto, AccountServiceDTO toAccountDto, BigDecimal amount) {
		
		//subtract required amount from senders's account
		fromAccountDto.setAvailableBalance(fromAccountDto.getAvailableBalance().subtract(amount));
		accountService.updateAccount(fromAccountDto.getAccountNumber(), fromAccountDto); 
		
		//add amount in receiver's account
		toAccountDto.setAvailableBalance(toAccountDto.getAvailableBalance().add(amount));
		accountService.updateAccount(toAccountDto.getAccountNumber(), toAccountDto);
		
		//to build transaction for both accounts
		List<TransactionServiceDTO> transactions = List.of(
				//set transaction dtos for senders
				TransactionServiceDTO.builder()
					.accountNumber(fromAccountDto.getAccountNumber())
					.transactionType("INTERNAL_TRANSFER")
					.amount(amount.negate())   //negate for withdrawal
					.description("Internal Fund Transferred from "+fromAccountDto.getAccountNumber() +" to "+toAccountDto.getAccountNumber())
					.build(),
					
				//set transaction dtos for receivers account
				TransactionServiceDTO.builder()
					.accountNumber(toAccountDto.getAccountNumber())
					.transactionType("INTERNAL_TRANSFER")
					.amount(amount)
					.description("Internal Fund Transfer received from "+fromAccountDto.getAccountNumber())
					.build()
				);
		
		//create transaction reference Id for transaction
		String trasactionReferenceId = UUID.randomUUID().toString();
		
		//Pass each transaction dtos to transaction service and convert each transaction dto into entity and save it 
		transactionService.internalTransaction(transactions, trasactionReferenceId);
		return trasactionReferenceId;
	}
	
	/*
	 retrieves transfer details from transactionReferenceId
	 param transaction referenceId
	 return type fundTranferDTO
	 */
	@Override
	public FundTransferDTO getTransferDetailsFromTransactionReferenceId(String transactionReferenceId) {
		
		return fundTransferRepository.findFundTransferByTransactionReferenceId(transactionReferenceId)
				.map(fundTransferMapper::toFullDTO)
				.orElseThrow(() -> new ResourceNotFoundException(GlobalErrorCode.NOT_FOUND, "Fund Transfer not found on the server"));
	}
	
	/*
	  retrieves all transfer details for given account Number
	  param accountNumber
	  return type List of FundTransferDTO
	 */
	@Override
	public List<FundTransferDTO> getAllTransfersByAccountNumber(String accountNumber){
		return fundTransferRepository.findFundTransferByFromAccount(accountNumber)
				.stream()
				.map(fundTransferMapper::toFullDTO)
				.collect(Collectors.toList());
	}
	
}
