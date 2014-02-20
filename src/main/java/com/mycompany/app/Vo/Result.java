package com.mycompany.app.Vo;

public class Result {
	private boolean success;
	private Object data;
	private String msg;
	
	public Result(boolean success) {
		super();
		this.success = success;
	}
	public Result(boolean success,Object data) {
		this(success);
		this.data = data;
	}
	public Result(Object data) {
		this(true);
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
