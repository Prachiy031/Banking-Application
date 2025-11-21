package org.create.bankingApplication.account_service.exceptions;

public class AccountClosingException extends GlobalException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;

	 public AccountClosingException(String errorMessage) {
	        super(GlobalErrorCode.BAD_REQUEST, errorMessage);
	    }
	 
}
