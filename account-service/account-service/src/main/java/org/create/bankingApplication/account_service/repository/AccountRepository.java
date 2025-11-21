package org.create.bankingApplication.account_service.repository;

import java.util.Optional;
import org.create.bankingApplication.account_service.model.AccountType;
import org.create.bankingApplication.account_service.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	//Repository will provide inbuilt methods to crud entity class with help of primary key 
	//customised methods apart from primary key
	
	/*
	 Find an account by userid and account type
	 return type account entity class' object
	 param are userdId, account Type 
	 */
	Optional<Account> findAccountByUserIdAndAccountType(Long userId, AccountType accounttype);
	
	/*
	 Find an account by its account number
	 return type acocunt entity class' object
	 param  accountNumber	  
	 */
	Optional<Account> findAccountByAccountNumber(String accountNumber);
	
	/*
	 Find account By UserId
	 return type acocunt entity class' object
	 param is userId
	 */
	Optional<Account> findAccountByUserId(Long userId);
}
