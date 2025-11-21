package org.create.bankingApplication.account_service.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.create.bankingApplication.account_service.model.AccountStatus;
import org.create.bankingApplication.account_service.model.AccountType;
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
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	private String accountNumber;
	
	@Enumerated(EnumType.STRING) //store data as string in enum
	private AccountType accountType;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;
	
	@CreationTimestamp
	private LocalDate accountOpeningDate;
	
	private BigDecimal availableBalance;
	
	private Long userId;
	
}
