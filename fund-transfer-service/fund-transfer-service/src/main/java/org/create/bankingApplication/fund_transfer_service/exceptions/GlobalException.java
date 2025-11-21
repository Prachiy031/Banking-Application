package org.create.bankingApplication.fund_transfer_service.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)       //equals and hascode methods will only consider fields from this class(e.g. errorcode)
//no fields will be considered from RuntimeException class 
//to considered fields from that class also use this annotation
//it will be helpful to compare two exceptions with message and code
public class GlobalException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errorMessage;
	
	public GlobalException(String errorMessage) {
		super(errorMessage);           //this call is for passing message to runtimeException, ensures logs, stack trace see correct error text though getMessage
		this.errorMessage = errorMessage;
	}
	
}
