package org.create.bankingApplication.user_service.model.external;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
	
	private Long accountId;

    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private BigDecimal availableBalance;

    private Long userId;
    
}
