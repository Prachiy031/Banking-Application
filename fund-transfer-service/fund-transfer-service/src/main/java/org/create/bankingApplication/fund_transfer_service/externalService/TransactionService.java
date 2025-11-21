package org.create.bankingApplication.fund_transfer_service.externalService;

import java.util.List;

import org.create.bankingApplication.fund_transfer_service.model.dto.external.TransactionServiceDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service")
public interface TransactionService {

	/*
    Adds a transaction
   
    param TransactionServiceRequestDTO The transaction to add
    return The responseDTO indicating whether the transaction was successfully added
   */
	

	@PostMapping("/transactions")
	ResponseEntity<ResponseDTO> addTransaction(@RequestBody TransactionServiceDTO transactionServiceRequestDto);

  /*
    Process an internal transaction
    [ handling multiple transactions in one go ]
   
    param transactionRequestDTOs The list of transactionRequest DTOs to process
    param transactionReference The transaction reference id
    return The responseDTO of the internal transaction
   */
	
	@PostMapping("/transactions/internal")
	ResponseEntity<ResponseDTO> internalTransaction(@RequestBody List<TransactionServiceDTO> transactionServiceRequestDtos, @RequestParam String referenceId);
	
}
