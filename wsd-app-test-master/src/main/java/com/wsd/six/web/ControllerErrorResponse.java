package com.wsd.six.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;

@XmlRootElement(name = "error")
public class ControllerErrorResponse {

    @XmlElement
    private int code;

    @XmlElement
    private String message;

    @XmlElement
    private String[] errors;


    public ControllerErrorResponse() {

    }


    public ControllerErrorResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
        errors = new String[0];
    }


    public ControllerErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
        errors = new String[0];
    }


    public ControllerErrorResponse(int code, String message, String[] errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }


    public String getMessage() {
        return message;
    }


    public int getCode() {
        return code;
    }


    public String[] getErrors() {
        return errors;
    }

}
