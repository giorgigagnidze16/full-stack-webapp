package io.fullstack.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<String> handleException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<String> handleException(AlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
            .getFieldErrors()
            .forEach(fieldError -> {
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errors.put(field, message);
            });

        return ResponseEntity
            .badRequest()
            .body(errors);
    }
}
