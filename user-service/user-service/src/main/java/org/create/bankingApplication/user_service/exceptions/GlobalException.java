package org.create.bankingApplication.user_service.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 Makes all exceptions extending this exception a unchecked exception by extending runtime exception
 this handles error code and error message
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)       //equals and hascode methods will only consider fields from this class(e.g. errorcode)
										//no fields will be considered from RuntimeException class 
										//to considered fields from that class also use this annotation
										//it will be helpful to compare two exceptions with message and code
public class GlobalException extends RuntimeException{
	
	//for java serialization needed this UID
	private static final long serialVersionUID = 1L;
		
	private String errorCode;

    public GlobalException(String message) {
        super(message);  // pass to RuntimeException
    }

    public GlobalException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
	
}
