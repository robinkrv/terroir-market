package fr.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleValidationExceptions(BindException ex) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
        }
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }
}

