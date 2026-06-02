package com.testTask.BalanceServiceTestTask.controller.handler;

import com.testTask.BalanceServiceTestTask.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> handleNotFoundUser(Exception ex){
        return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
    }

}
