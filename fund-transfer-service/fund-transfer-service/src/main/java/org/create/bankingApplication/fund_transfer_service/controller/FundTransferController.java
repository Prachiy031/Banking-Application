package org.create.bankingApplication.fund_transfer_service.controller;

import java.util.List;

import org.create.bankingApplication.fund_transfer_service.model.dto.FundTransferDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.requestDTO.FundTransferRequestDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO.FundTransferResponseDTO;
import org.create.bankingApplication.fund_transfer_service.service.FundTransferService;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fund-transfers")
@RequiredArgsConstructor
public class FundTransferController {
	
	
	private final FundTransferService fundTransferService;
	/*
	 Fund transfer
	 transfers fund from one account to another
	 param fundTranferRequestDTO
	 return type FundTransferResponseDTo 
	 */
	@PostMapping
	public ResponseEntity<FundTransferResponseDTO> fundTransfer(@RequestBody FundTransferRequestDTO fundTranferRequestDto){
		return new ResponseEntity<>(fundTransferService.fundTransfer(fundTranferRequestDto), HttpStatus.CREATED);
	}
	
	/*
	 retrieves transfer details from transactionReferenceId
	 param referenceId
	 return type fundTranferDTO
	 */
	@GetMapping("/{transactionReferenceId}")
	public ResponseEntity<FundTransferDTO> getTransferDetailsFromTransactionReferenceId(@PathVariable String transactionReferenceId) {
		return new ResponseEntity<>(fundTransferService.getTransferDetailsFromTransactionReferenceId(transactionReferenceId), HttpStatus.OK);
	}
	
	/*
	  retrieves all transfer details for given account Number
	  param accountNumber
	  return type List of FundTransferDTO
	 */
	@GetMapping
	public ResponseEntity<List<FundTransferDTO>> getAllTransfersByAccountNumber(@RequestParam String accountNumber){
		return new ResponseEntity<>(fundTransferService.getAllTransfersByAccountNumber(accountNumber), HttpStatus.OK);
	}
	
}
