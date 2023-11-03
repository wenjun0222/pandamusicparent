package com.ning;

import java.io.Serializable;
public class ResponseResult<Data> implements Serializable {
    public Data data;
    public  String message;
    public  int code;
    public static ResponseResult success(){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setCode(HttpStatusCode.SUCCESS.getCode());
        responseResult.setMessage(HttpStatusCode.SUCCESS.getMessage());
        return responseResult;
    }
    public static ResponseResult success(HttpStatusCode httpStatusCode){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setCode(httpStatusCode.SUCCESS.getCode());
        responseResult.setMessage(httpStatusCode.SUCCESS.getMessage());
        return responseResult;
    }
    public static <Data> ResponseResult success(Data data){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setCode(HttpStatusCode.SUCCESS.getCode());
        responseResult.setMessage(HttpStatusCode.SUCCESS.getMessage());
        responseResult.setData(data);
        return responseResult;
    }
    public static ResponseResult fail(HttpStatusCode httpStatusCode){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setCode(httpStatusCode.getCode());
        responseResult.setMessage(httpStatusCode.getMessage());
        return responseResult;
    }
    public static <Data> ResponseResult error(Data data){
        ResponseResult responseResult=new ResponseResult();
        responseResult.setCode(HttpStatusCode.MUSIC_NULL.getCode());
        responseResult.setMessage(HttpStatusCode.MUSIC_NULL.getMessage());
        responseResult.setData(data);
        return responseResult;
    }

    public void error(HttpStatusCode httpStatusCode){

    }
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
