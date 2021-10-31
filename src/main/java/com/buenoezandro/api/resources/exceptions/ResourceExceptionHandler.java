package com.buenoezandro.api.resources.exceptions;

import com.buenoezandro.api.services.exceptions.EmailAlreadyExistsException;
import com.buenoezandro.api.services.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(UserNotFoundException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public ResponseEntity<StandardError> emailAlreadyExists(EmailAlreadyExistsException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
