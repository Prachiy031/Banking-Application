package org.create.bankingApplication.transaction_service.model.responseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionServiceResponseDTO {
	
	private String referenceId;

    private Long accountId;
    
    private String accountNumber;

    private String transactionType;

    private BigDecimal amount;

    private LocalDateTime localDateTime;

    private String transactionStatus;

    private String comments;
    
}
