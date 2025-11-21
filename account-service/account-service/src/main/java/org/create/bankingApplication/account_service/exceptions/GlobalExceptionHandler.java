package org.create.bankingApplication.account_service.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice //controllerAdvice + responseBody =>this will automatically add responseBody ...no need to add this annotation 
//converts all returned objects to json / xml format{via jackson}

//class used to handle all exceptions
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Value("${spring.application.bad_request}")
    private String badRequest;

    @Value("${spring.application.conflict}")
    private String conflict;

    @Value("${spring.application.not_found}")
    private String notFound;
    
    /*
     instead of handling thrown exceptions there in controller class methods
     and increasing code length 
     write handling methods in different class as we have written here
     controller->throws exception->checks any exceptionhandler method defined for that exception
     ->if yes then call that method -> and execute code inside that method->convert object into json format
     */
    //this is overriden method from ResponseEntityExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(new ErrorResponse(badRequest, ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
    
    
    
    /*
      Exception handler for GlobalException
     
      param globalException The GlobalException to handle.
      return The ResponseEntity with the error response.
     */
    @ExceptionHandler(GlobalException.class)    //catch block for that exception
    public ResponseEntity<Object> handleGlobalException(GlobalException globalException) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .errorCode(globalException.getErrorCode())
                        .message(globalException.getErrorMessage())
                        .build());
    }
    
}
