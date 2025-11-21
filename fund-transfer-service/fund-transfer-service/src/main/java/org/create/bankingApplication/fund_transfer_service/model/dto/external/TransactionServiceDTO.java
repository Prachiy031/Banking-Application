package org.create.bankingApplication.fund_transfer_service.model.dto.external;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionServiceDTO {
	
	private String accountNumber;
	
    private String transactionType;

    private BigDecimal amount;      //amount to withdraw or deposit in account
	
    private String description;
}
