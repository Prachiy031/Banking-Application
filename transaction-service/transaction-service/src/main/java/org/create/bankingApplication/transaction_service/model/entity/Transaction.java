package org.create.bankingApplication.transaction_service.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.create.bankingApplication.transaction_service.model.TransactionStatus;
import org.create.bankingApplication.transaction_service.model.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	private String referenceId;
	
	private String accountId;
	
	private String accountNumber;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	private BigDecimal amount;    //amount to withdraw or deposit in account
	
	@CreationTimestamp           //The timestamp is generated just once, 
								//when an entity instance is inserted in the database. 
	private LocalDateTime transactionDate;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;
	
	private String comments;	
}
