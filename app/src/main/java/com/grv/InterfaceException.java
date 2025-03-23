package com.grv;

public class InterfaceException extends Exception {
    private final int idString;
    public InterfaceException(String message, Throwable cause, int idString) {
        super(message, cause);
        this.idString = idString;
    }

    public InterfaceException(String message, int idString) {
        super(message);
        this.idString = idString;
    }
    public InterfaceException(int idString) {
        super("IdString do erro de interface:" + idString);
        this.idString = idString;
    }

    public InterfaceException(Throwable cause, int idString) {
        super(cause);
        this.idString = idString;
    }

    public int getIdString() {
        return idString;
    }
}
