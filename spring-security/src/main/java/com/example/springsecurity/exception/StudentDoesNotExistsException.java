package com.example.springsecurity.exception;

public class StudentDoesNotExistsException extends RuntimeException{
    public StudentDoesNotExistsException(String message) {
        super(message);
    }
}
