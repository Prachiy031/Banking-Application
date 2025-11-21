package org.create.bankingApplication.fund_transfer_service.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Value("${spring.application.bad_request}")
    private String badRequest;
	
	/**
      Handles the method argument validation exception => if validation from dto doesen't match then 
      exeption is thrown 
     
      param ex      The MethodArgumentNotValidException to handle
      param headers The HttpHeaders to include in the response
      param status  The HttpStatus to set in the response
      param request The WebRequest associated with the request
      return A ResponseEntity containing an ErrorResponse and HttpStatus.BAD_REQUEST
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(new ErrorResponse(badRequest, ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
    
	/*
	 Handles exception for global exception 
	 Creates errorResponse Object
	 and return in form of responseEntity
	 */
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<Object> handleGlobalException(GlobalException globalException){
		return ResponseEntity.badRequest()
				.body(ErrorResponse.builder()
						.errorCode(globalException.getErrorCode())
						.errorMessage(globalException.getErrorMessage())
						.build());
	} 
	
}
