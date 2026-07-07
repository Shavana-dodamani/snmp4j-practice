package com.exampl.snmp.exception;

public class SnmpException extends RuntimeException {

    public SnmpException(String message) {
        super(message);
    }

    public SnmpException(String message, Throwable cause) {
        super(message, cause);
    }
}