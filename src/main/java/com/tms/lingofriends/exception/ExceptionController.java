package com.tms.lingofriends.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice
@ResponseBody
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> notFoundExceptionHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionMessage(HttpStatus.NOT_FOUND, Timestamp.valueOf(LocalDateTime.now()), exception.getMessage()));
    }

    @ExceptionHandler(BadReqException.class)
    public ResponseEntity<ExceptionMessage> BadReqExceptionHandler(BadReqException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionMessage(HttpStatus.BAD_REQUEST, Timestamp.valueOf(LocalDateTime.now()), exception.getMessage()));
    }
}
