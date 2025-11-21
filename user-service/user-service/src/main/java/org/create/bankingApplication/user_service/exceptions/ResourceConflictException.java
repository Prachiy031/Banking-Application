package org.create.bankingApplication.user_service.exceptions;

public class ResourceConflictException extends GlobalException{
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;
	//with code null
	public ResourceConflictException() {
		super("Resource already present on the server.");
	}
		
	//with detailed message
	public ResourceConflictException(String message) {
		super(message);
	}
	
}
