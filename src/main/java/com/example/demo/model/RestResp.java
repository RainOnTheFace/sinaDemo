package com.example.demo.model;

import java.io.Serializable;

public class RestResp implements Serializable {
    private static final long serialVersionUID = -2239148517975981071L;
    private String code;
    private String msg;
    private Object data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RestResp() {
    }

    public RestResp(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }



    public void setParamError() {
        this.setCode("E210001");
        this.setMsg("参数有误");
        this.setData((Object)null);
    }

    public void setCodeError() {
        this.setCode("E210005");
        this.setMsg("验证码错误");
        this.setData((Object)null);
    }

    public void setException() {
        this.setCode("E210006");
        this.setMsg("操作异常");
        this.setData((Object)null);
    }

    public void setUnExist() {
        this.setCode("E210010");
        this.setMsg("请求信息不存在");
        this.setData((Object)null);
    }

    public void setBindMobile() {
        this.setCode("E210007");
        this.setMsg("请绑定手机");
        this.setData((Object)null);
    }

    public void setUnBind() {
        this.setCode("E210011");
        this.setMsg("微信已绑定其他手机");
        this.setData((Object)null);
    }

    public void setLock() {
        this.setCode("E210012");
        this.setMsg("账户锁定中");
        this.setData((Object)null);
    }

    public void setNotEnoughCoin() {
        this.setCode("E210013");
        this.setMsg("牛币余额不足");
        this.setData((Object)null);
    }

    public void setNotEnoughCoupon() {
        this.setCode("E210014");
        this.setMsg("牛券余额不足");
        this.setData((Object)null);
    }

    public void setTimeValid() {
        this.setCode("E210004");
        this.setMsg("请求已过期");
        this.setData((Object)null);
    }

    public void setSignKey() {
        this.setCode("E210003");
        this.setMsg("加密有误");
        this.setData((Object)null);
    }

    public void setParamExc() {
        this.setCode("E210002");
        this.setMsg("参数非法");
        this.setData((Object)null);
    }

    @Override
    public String toString() {
        return "RestResp{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}