package org.create.bankingApplication.user_service.model.dto.externalDto;

import java.math.BigDecimal;

import jakarta.transaction.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountServiceReponseExternalDTO {
	
	private String accountNo;
	
	private BigDecimal actualBalance;
	
	private Integer accountId;
	
	private String accountType;
	
	private Status status;
	
	private BigDecimal availableBalance;
}
