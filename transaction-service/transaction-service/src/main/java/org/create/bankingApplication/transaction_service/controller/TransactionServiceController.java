package org.create.bankingApplication.transaction_service.controller;

import java.util.List;

import org.create.bankingApplication.transaction_service.model.dto.TransactionServiceRequestDTO;
import org.create.bankingApplication.transaction_service.model.responseDTO.ResponseDTO;
import org.create.bankingApplication.transaction_service.model.responseDTO.TransactionServiceResponseDTO;
import org.create.bankingApplication.transaction_service.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionServiceController {
	
	
	private final TransactionService transactionService;
	
	//method names are kept same as mentioned in service layer for sake of easy understanding
	/*
    Adds a transaction
   
    param TransactionServiceRequestDTO The transaction to add
    return The responseDTO indicating whether the transaction was successfully added
   */
	@PostMapping
	public ResponseEntity<ResponseDTO> addTransaction(@RequestBody TransactionServiceRequestDTO transactionServiceRequestDto) {
		return new ResponseEntity<>(transactionService.addTransaction(transactionServiceRequestDto), HttpStatus.CREATED);
	}

  /*
    Process an internal transaction
    [ handling multiple transactions in one go ]
   
    param transactionRequestDTOs The list of transactionRequest DTOs to process
    param transactionReference The transaction reference id
    return The responseDTO of the internal transaction
   */
	@PostMapping("/internal")
	public ResponseEntity<ResponseDTO> internalTransaction(@RequestBody List<TransactionServiceRequestDTO> transactionServiceRequestDtos,@RequestParam String referenceId){
		return new ResponseEntity<>(transactionService.internalTransaction(transactionServiceRequestDtos, referenceId), HttpStatus.CREATED);
	}

  /*
    Retrieves a list of transactionResponseDTOs for a given account Number
   
    param accountNumber 
    return a list of transactionRequestDTOs
   */
	@GetMapping
	public ResponseEntity<List<TransactionServiceResponseDTO>> getTransaction(@Valid @RequestParam String accountNumber){
		return new ResponseEntity<>(transactionService.getTransactionByAccountNumber(accountNumber),HttpStatus.OK);
	}

  /*
    Retrieves a list of transaction Response by transaction reference id 
   
    param transactionReference The transaction reference to search for 
    return A list of transaction requests matching the given transaction reference id
   */
	@GetMapping("/{referenceId}")
	public ResponseEntity<List<TransactionServiceResponseDTO>> getTransactionByReferenceId(@PathVariable String referenceId){
		return new ResponseEntity<>(transactionService.getTransactionByReferenceId(referenceId), HttpStatus.OK);
	}
  
}
