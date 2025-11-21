package org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundTransferResponseDTO {
	
	private String transactionReferenceId;

    private String transactionMessage;
    
}
