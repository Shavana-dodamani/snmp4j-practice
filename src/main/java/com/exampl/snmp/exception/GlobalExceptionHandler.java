package com.exampl.snmp.exception;

import com.exampl.snmp.dto.SnmpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SnmpException.class)
    public SnmpResponse handleSnmpException(SnmpException ex) {

        return SnmpResponse.builder()
                .status("FAILED")
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public SnmpResponse handleException(Exception ex) {

        return SnmpResponse.builder()
                .status("FAILED")
                .message(ex.getMessage())
                .build();
    }
}