package org.create.bankingApplication.fund_transfer_service.exceptions;

public class InsufficientBalanceException extends GlobalException{
	
	/*for serialization purpose*/
	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
