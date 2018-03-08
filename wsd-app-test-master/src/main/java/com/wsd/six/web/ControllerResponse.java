package com.wsd.six.web;

public class ControllerResponse {

    private boolean success;

    private String message;


    protected ControllerResponse(boolean success, String message) {
	this.success = success;
        this.message = message;
    }
    

    public static ControllerResponse success() {
        return new ControllerResponse(true, null);
    }

    public static ControllerResponse success(String message) {
        return new ControllerResponse(true, message);
    }

    public static ControllerResponse fail(String message) {
        return new ControllerResponse(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean b) {
	success = b;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String s) {
	message = s;
    }
    
}
