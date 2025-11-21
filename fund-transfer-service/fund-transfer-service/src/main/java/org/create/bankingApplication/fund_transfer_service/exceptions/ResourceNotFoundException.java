package org.create.bankingApplication.fund_transfer_service.exceptions;

public class ResourceNotFoundException extends GlobalException{
	
	/*for serialization purpose*/
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
