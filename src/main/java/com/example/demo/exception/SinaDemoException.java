package com.example.demo.exception;


import com.example.demo.utils.RespCode;

/**
 * Created with IntelliJ IDEA.
 * User: wangqingxin
 * Date: 2018/11/15
 * Time: 16:07
 * Description: 招采Exception
 **/
public class SinaDemoException extends RuntimeException {

    private Integer errorCode;
    private String message;

    public SinaDemoException() {
        super();
    }

    public SinaDemoException(String message) {
        this.errorCode = RespCode.ERROR.getCode();
        this.message = message;
    }

    public SinaDemoException(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public SinaDemoException(RespCode respCode, String message) {
        this.errorCode = respCode.getCode();
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
