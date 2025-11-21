package org.create.bankingApplication.account_service.exceptions;


public class ResourceConflictException extends GlobalException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;

	public ResourceConflictException() {
        super("Account already exists", GlobalErrorCode.CONFLICT);
    }

    public ResourceConflictException(String message) {
        super(message, GlobalErrorCode.CONFLICT);
    }
}
