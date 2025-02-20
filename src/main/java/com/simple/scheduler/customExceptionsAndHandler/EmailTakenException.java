package com.simple.scheduler.customExceptionsAndHandler;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String message) {
        super(message);
    }
}
