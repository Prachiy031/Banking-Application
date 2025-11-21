package org.create.bankingApplication.transaction_service.model.dto.external;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountServiceDTO {
	
	private Long accountId;

    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private BigDecimal availableBalance;

    private Long userId;
    
}
