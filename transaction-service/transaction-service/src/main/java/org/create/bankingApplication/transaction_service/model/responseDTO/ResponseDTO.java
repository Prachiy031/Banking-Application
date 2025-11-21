package org.create.bankingApplication.transaction_service.model.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
	
	private String responseCode;
	
	private String responseMessage;
	
}
