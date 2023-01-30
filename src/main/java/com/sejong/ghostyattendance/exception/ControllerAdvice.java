package com.sejong.ghostyattendance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ParsingException.class)
    public ResponseEntity<ErrorResponse> handleParsingException(ParsingException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
    }
}
