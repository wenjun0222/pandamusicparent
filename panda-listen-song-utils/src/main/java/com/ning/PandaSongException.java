package com.ning;

public class PandaSongException extends RuntimeException{
    private int code;
    private String message;
    public PandaSongException(int code,String message){
        this.code=code;
        this.message=message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PandaSongException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
