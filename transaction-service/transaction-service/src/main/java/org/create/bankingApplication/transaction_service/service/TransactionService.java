package org.create.bankingApplication.transaction_service.service;

import java.util.List;
import org.create.bankingApplication.transaction_service.model.dto.TransactionServiceRequestDTO;
import org.create.bankingApplication.transaction_service.model.responseDTO.ResponseDTO;
import org.create.bankingApplication.transaction_service.model.responseDTO.TransactionServiceResponseDTO;

public interface TransactionService {
	
	/*
      Adds a transaction
     
      param TransactionServiceRequestDTO The transaction to add
      return The responseDTO indicating whether the transaction was successfully added
     */
    ResponseDTO addTransaction(TransactionServiceRequestDTO transactionServiceRequestDto);

    /*
      Process an internal transaction
      [ handling multiple transactions in one go ]
     
      param transactionRequestDTOs The list of transactionRequest DTOs to process
      param transactionReference The transaction reference id
      return The responseDTO of the internal transaction
     */
    ResponseDTO internalTransaction(List<TransactionServiceRequestDTO> transactionServiceRequestDtos, String referenceId);

    /*
      Retrieves a list of transactionResponseDTOs for a given account number
     
      param accountNumber 
      return a list of transactionRequestDTOs
     */
    List<TransactionServiceResponseDTO> getTransactionByAccountNumber(String accountNumber);

    /*
      Retrieves a list of transaction Response by transaction reference id 
     
      param transactionReference The transaction reference to search for 
      return A list of transaction requests matching the given transaction reference id
     */
    List<TransactionServiceResponseDTO> getTransactionByReferenceId(String referenceId);
		
}
