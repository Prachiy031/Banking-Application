package org.create.bankingApplication.account_service.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

/*	
 	acts as request and response dto both
	no need to add validations here as mostly this dto will work as reponse dto
*/
public class AccountDTO {
	
	private Long accountId;

    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private BigDecimal availableBalance;

    private Long userId;
    
}
