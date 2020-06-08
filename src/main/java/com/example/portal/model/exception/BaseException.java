package com.example.portal.model.exception;

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Code code) {
        super(code.getMessage());
    }
}
