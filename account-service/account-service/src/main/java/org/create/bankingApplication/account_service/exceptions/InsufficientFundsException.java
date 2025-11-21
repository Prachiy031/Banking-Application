package org.create.bankingApplication.account_service.exceptions;

public class InsufficientFundsException extends GlobalException{

	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException() {
        super("Insufficient funds", GlobalErrorCode.NOT_FOUND);
    }

    public InsufficientFundsException(String message) {
        super(message, GlobalErrorCode.NOT_FOUND);
    }
}
