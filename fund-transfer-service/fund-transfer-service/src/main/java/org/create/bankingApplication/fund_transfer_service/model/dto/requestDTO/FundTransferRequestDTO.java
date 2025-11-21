package org.create.bankingApplication.fund_transfer_service.model.dto.requestDTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class FundTransferRequestDTO {
	
	@NotBlank(message = "Sender's account number is required")
	private String fromAccount;

	@NotBlank(message = "Receiver's account number is required")
    private String toAccount;

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount should be positive")
    private BigDecimal amount;
    
}
