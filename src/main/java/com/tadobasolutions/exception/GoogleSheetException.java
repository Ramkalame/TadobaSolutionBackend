package com.tadobasolutions.exception;

public class GoogleSheetException extends RuntimeException{
    public GoogleSheetException(String message) {
        super(message);
    }

    public GoogleSheetException(String message, Throwable cause) {
        super(message, cause);
    }
}
