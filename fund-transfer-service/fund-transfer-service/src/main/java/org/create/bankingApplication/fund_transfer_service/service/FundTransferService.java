package org.create.bankingApplication.fund_transfer_service.service;

import java.util.List;

import org.create.bankingApplication.fund_transfer_service.model.dto.FundTransferDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.requestDTO.FundTransferRequestDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO.FundTransferResponseDTO;

public interface FundTransferService {
	
	/*
	 Fund transfer
	 transfers fund from one account to another
	 param fundTranferRequestDTO
	 return type FundTransferDTo 
	 */
	
	FundTransferResponseDTO fundTransfer(FundTransferRequestDTO fundTranferRequestDto);
	
	/*
	 retrieves transfer details from transactionReferenceId
	 param referenceId
	 return type fundTranferDTO
	 */
	FundTransferDTO getTransferDetailsFromTransactionReferenceId(String transactionReferenceId);
	
	/*
	  retrieves all transfer details for given account Number
	  param accountNumber
	  return type List of FundTransferDTO
	 */
	List<FundTransferDTO> getAllTransfersByAccountNumber(String accountNumber);
	
}
