package org.create.bankingApplication.transaction_service.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 Request DTO 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionServiceRequestDTO {
	
	@NotNull(message = "Account Number cannot be null")
	private String accountNumber;
	
	@NotBlank(message = "Transaction type is required among DEPOSIT/ WITHDRAWAL/ TRANSFER")
    private String transactionType;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be greater than zero")   //At least 0.01 value required
    private BigDecimal amount;      //amount to withdraw or deposit in account
	
	@Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

}
