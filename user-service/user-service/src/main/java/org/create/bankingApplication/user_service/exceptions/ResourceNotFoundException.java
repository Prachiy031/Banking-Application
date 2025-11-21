package org.create.bankingApplication.user_service.exceptions;

public class ResourceNotFoundException extends GlobalException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;
		
	public ResourceNotFoundException() {
		super("Resource not found on the server.",GlobalError.NOT_FOUND);
	}
	
	public ResourceNotFoundException(String message) {
		super(message,GlobalError.NOT_FOUND);
	}
	
}
