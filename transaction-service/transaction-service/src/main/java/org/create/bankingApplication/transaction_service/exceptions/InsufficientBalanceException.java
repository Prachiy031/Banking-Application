package org.create.bankingApplication.transaction_service.exceptions;


public class InsufficientBalanceException extends GlobalException{
	
	//for serializable class
	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String errorMessage) {
        super(errorMessage, GlobalErrorCode.BAD_REQUEST);
    }
	
}
