package org.create.bankingApplication.transaction_service.repository;

import java.util.List;

import org.create.bankingApplication.transaction_service.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionServiceRepository extends JpaRepository<Transaction, Long>{
		
	//inbuilt methods are available to do crud with help of primary key
	//below are some extra methods apart from primary key use
	
	/*
	 retrieve transaction based on account number
	 return type List of Transaction Entity
	 param account number
	 */
	List<Transaction> findTransactionByAccountNumber(String accountNumber);
	
	
	/*
	 retrieve list of transactions based on reference Id
	 return type List of transaction entity for given reference id
	 param referenec Id	 
	 * */
	List<Transaction> findTransactionByReferenceId(String referenceId);
}
