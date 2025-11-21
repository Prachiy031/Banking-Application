package org.create.bankingApplication.transaction_service.exceptions;

public class AccountStatusException extends GlobalException{
	
	//for serializable class
	private static final long serialVersionUID = 1L;
	
	public AccountStatusException(String message) {
        super(message, GlobalErrorCode.BAD_REQUEST);
    }
	
}
