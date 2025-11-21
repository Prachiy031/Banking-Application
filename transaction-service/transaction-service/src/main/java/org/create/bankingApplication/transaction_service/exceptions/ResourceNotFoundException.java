package org.create.bankingApplication.transaction_service.exceptions;

public class ResourceNotFoundException extends GlobalException{
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
	
}
