package com.application.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class WalletRestControllerAdvice {


    @ExceptionHandler(WalletException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleWalletException(WalletException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleEmployeeException(Exception e){
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> inputException(MethodArgumentNotValidException e){
        Map<String, String> exceptions = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->{
            String inputName =((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            exceptions.put(inputName,errorMessage);

        });
        return exceptions;
    }
}
