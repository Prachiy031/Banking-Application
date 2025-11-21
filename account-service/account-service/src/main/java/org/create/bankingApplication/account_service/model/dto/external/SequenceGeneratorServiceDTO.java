package org.create.bankingApplication.account_service.model.dto.external;

import lombok.Data;

/*Response DTO from SequenceGeneratorService*/
@Data
public class SequenceGeneratorServiceDTO {
	
	private long sequenceId;

    private long accountNumber;
    
}
