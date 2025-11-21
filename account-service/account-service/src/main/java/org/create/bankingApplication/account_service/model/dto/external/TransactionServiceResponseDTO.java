package org.create.bankingApplication.account_service.model.dto.external;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Response DTO from TransactionService*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionServiceResponseDTO {
	
	private String referenceId;

    private String accountId;

    private String transactionType;

    private BigDecimal amount;

    private LocalDateTime localDateTime;

    private String transactionStatus;

    private String comments;
}
