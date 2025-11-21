package org.create.bankingApplication.account_service.exceptions;

public class AccountStatusException extends GlobalException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;

	public AccountStatusException(String errorMessage) {
        super(errorMessage, GlobalErrorCode.BAD_REQUEST);
    }
	
}
