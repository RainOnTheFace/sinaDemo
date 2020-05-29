package com.example.demo.utils;

/**
 * 返回值代码
 */
public enum RespCode {

	/**
	 * 系统
	 */
	SUCCESS(1, "成功"), ERROR(0, "系统错误"), PARAMETER_INVALID(999, "参数错误(调试使用,正常不会出现)"),
	/**
	 * 登录相关
	 */
	SESSIONOUT(400, "请重新登录"),
	/**
	 * 资源相关
	 */
	RESOURCE_EXIST(800, "资源已经存在"), RESOURCE_NONEXIST(801, "资源不存在"), ACCESS_FORBID(802, "无权限访问");

	private int code;
	private String detail;

	private RespCode(int code, String detail) {
		this.code = code;
		this.detail = detail;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
