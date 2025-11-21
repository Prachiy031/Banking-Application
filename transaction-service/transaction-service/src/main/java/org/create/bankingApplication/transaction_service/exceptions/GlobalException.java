package org.create.bankingApplication.transaction_service.exceptions;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/*
 Initiate every exception exetending this exception
 makes every exception RuntimeException
 */

@Data
@AllArgsConstructor 
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)       //equals and hascode methods will only consider fields from this class(e.g. errorcode)
//no fields will be considered from RuntimeException class 
//to considered fields from that class also use this annotation
//it will be helpful to compare two exceptions with message and code

public class GlobalException extends RuntimeException{
	
	//for serializable class
	private static final long serialVersionUID = 1L;

	private String errorCode;
	
	private String errorMessage;
	
	public GlobalException(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
}
