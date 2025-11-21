package org.create.bankingApplication.user_service.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice         //applies to all controllers
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { //ResponseEntityExceptionHandler allows handling Spring MVC exceptions in a clean way

    @Value("${spring.application.bad_request}")
    private String errorCodeBadRequest;

    @Value("${spring.application.conflict}")
    private String errorCodeConflict;

    @Value("${spring.application.not_found}")
    private String errorCodeNotFound;

    /**
      Handles the case when a method argument is not valid
     
      @param ex The MethodArgumentNotValidException that was thrown
      @param headers The HttpHeaders object for the response
      @param status The HttpStatus for the response
      @param request The WebRequest object for the response
      @return A ResponseEntity containing an ErrorResponse object with the error code and localized message, and the HTTP status code
     */
    
    //inbuilt method for customising validation related errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		MethodArgumentNotValidException ex, 
    		HttpHeaders headers, 
    		HttpStatusCode status, 
    		WebRequest request) {
    		
    	//returns customised response containing error code, localised message, http status code
        return new ResponseEntity<>(new ErrorResponseException(errorCodeBadRequest, 
        														ex.getLocalizedMessage()), 
        														HttpStatus.BAD_REQUEST);
    }

    /*
      Handles global exceptions
     
      @param globalException The global exception to handle.
      @return The response entity with the error response.
     */
    
    //user defined method for cutstomising custom eexceptions and send consistent api response
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleGlobalException(GlobalException globalException) {

        return ResponseEntity
                .badRequest()
                .body(ErrorResponseException.builder()
                        .errorMessage(globalException.getMessage())         //adds errormessage
                        .errorCode(globalException.getErrorCode())           //adds error code
                        .build());                                    //return josn entity by building into object
    }

}
