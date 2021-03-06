package com.wsd.six.web;


public class ControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

}
