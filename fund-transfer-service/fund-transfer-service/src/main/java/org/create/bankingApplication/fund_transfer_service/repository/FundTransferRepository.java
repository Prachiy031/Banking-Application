package org.create.bankingApplication.fund_transfer_service.repository;

import java.util.List;
import java.util.Optional;
import org.create.bankingApplication.fund_transfer_service.model.entity.FundTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundTransferRepository extends JpaRepository<FundTransfer, Long>{
	
	/*
	 All methods are in built for crud operations using primary key  
	 other than primary key , have to declare here 
	 */
	
	/*
	 Find fund transfer by transaction Reference Id 
	 param reference id 
	 return type Optional type FundTransfer class' object
	 */
	Optional<FundTransfer> findFundTransferByTransactionReferenceId(String transactionReferenceId); 
	
	/*
	 Find all fund transfer for sender's account number
	 param sender's account number
	 return type List of FundTranfer class' object 
	 */
	List<FundTransfer> findFundTransferByFromAccount(String fromAccount);
	
	
}
