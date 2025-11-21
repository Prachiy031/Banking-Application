package org.create.bankingApplication.account_service.exceptions;


/*base exception for 
 all other exception
 */
public class GlobalException extends RuntimeException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;

	private final String errorMessage;
	
	private final String errorCode;

	public GlobalException(String errorMessage, String errorCode) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
