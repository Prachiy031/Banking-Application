package org.create.bankingApplication.fund_transfer_service.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.create.bankingApplication.fund_transfer_service.model.TransactionStatus;
import org.create.bankingApplication.fund_transfer_service.model.TransferType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 DTO for internal use and response purpose for retrieving transfer details
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundTransferDTO {

	    private String transactionReferenceId;

	    private String fromAccount;

	    private String toAccount;

	    private BigDecimal amount;

	    private TransactionStatus status;

	    private TransferType transferType;

	    private LocalDateTime transferredOn;
}
