package org.create.bankingApplication.user_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseException {
	
	private String errorCode;
	
	private String errorMessage;
}
