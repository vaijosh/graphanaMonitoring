package com.qualys.apps.helloword.exceptions;

public class BackendServiceException extends Exception {
    private int code;

    public int getCode(){
        return code;
    }

    public BackendServiceException(Throwable cause){
        super(cause);
    }
    public BackendServiceException(int code, String message){
        super(message);
        this.code = code;
    }
    public BackendServiceException(int code, String message, Exception e){
        super(message, e);
        this.code = code;
    }
}
