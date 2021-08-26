package com.github.braians.springblog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(Throwable ex, Object body, WebRequest request) {
       
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST,"Server error", ex.getLocalizedMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest  ){

    return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND,ex.getLocalizedMessage()));
   }

   @ExceptionHandler(ResourceAlreadyExistException.class)
   protected ResponseEntity<Object> handleResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest webRequest  ){

   return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage()));
  }

  @ExceptionHandler(UnauthorizedException.class)
   protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, WebRequest webRequest  ){

   return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED,ex.getLocalizedMessage()));
  }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

         ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation Failed");
        
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
          apiError.addErros(fieldError.getField(), fieldError.getDefaultMessage());
        }
     
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
      
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<Object>(apiError, apiError.getStatus());
    }
 
}
