package com.mycompany.app.Vo;

public class Result {
	private boolean success;
	public Result(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
