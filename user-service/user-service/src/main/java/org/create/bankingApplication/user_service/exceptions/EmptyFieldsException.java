package org.create.bankingApplication.user_service.exceptions;

public class EmptyFieldsException extends GlobalException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;
	
	public EmptyFieldsException(String message, String errorCode) {
		super(message, errorCode);
	}
	
}
