package com.grv;

public class InterfaceException extends Exception {
    public InterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterfaceException(String message) {
        super(message);
    }

    public InterfaceException(Throwable cause) {
        super(cause);
    }
}
