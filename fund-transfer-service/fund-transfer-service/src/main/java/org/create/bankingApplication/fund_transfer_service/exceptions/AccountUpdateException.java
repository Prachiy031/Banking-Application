package org.create.bankingApplication.fund_transfer_service.exceptions;

public class AccountUpdateException extends GlobalException{
	
	/*for serialization purpose*/
	private static final long serialVersionUID = 1L;

	public AccountUpdateException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
