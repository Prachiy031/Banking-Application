package org.create.bankingApplication.transaction_service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice    //controllerAdvice + responseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	/*
	  handles global exception and sets value in ErrorResponse with message and status code
	  and return responseEntity in json format
	 */
	
	/*
	 handles exceptions in globalException class
	 this annotation tells spring=> Whenever a GlobalException is thrown in any controller, call this method to handle it
	 => method name is custom
	 */
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<Object> handleGlobalException(GlobalException globalException){
		
		return ResponseEntity
				.badRequest()           //set response status to badrequest
				.body(ErrorResponse.builder()
						.errorCode(globalException.getErrorCode())
						.errorMessage(globalException.getErrorMessage())
						.build());
	}
	
	
}
