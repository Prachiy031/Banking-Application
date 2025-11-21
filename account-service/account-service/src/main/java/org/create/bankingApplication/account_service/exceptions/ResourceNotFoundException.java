package org.create.bankingApplication.account_service.exceptions;


public class ResourceNotFoundException extends GlobalException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
        super("Resource not found on the server", GlobalErrorCode.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message, GlobalErrorCode.NOT_FOUND);
    }
    
}
