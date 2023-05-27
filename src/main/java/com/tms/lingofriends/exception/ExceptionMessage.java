package com.tms.lingofriends.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private HttpStatus statusCode;
    private Timestamp timestamp;
    private String message;
}
