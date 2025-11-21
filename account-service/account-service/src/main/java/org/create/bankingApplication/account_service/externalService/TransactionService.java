package org.create.bankingApplication.account_service.externalService;

import java.util.List;

import org.create.bankingApplication.account_service.model.dto.external.TransactionServiceResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service")
public interface TransactionService {
	/**
      Retrieves a list of transactions from the specified account ID.
     
      param accountId the ID of the account
      return a list of transaction service response dto
     */
    @GetMapping("/transactions")
    List<TransactionServiceResponseDTO> getTransactionsByAccountNumber(@RequestParam String accountNumber);
}
