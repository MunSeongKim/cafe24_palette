package com.cafe24.mammoth.app.support;

public class JSONResult {
	private String result;	// "success" or "fail"
	private String message;	
	private Object data;	
	
	private JSONResult(String result, String message, Object data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public static JSONResult success(Object data) {
		return new JSONResult("success", null, data);
	}
	public static JSONResult fail(String message) {
		return new JSONResult("fail", message, null);
	}	
	
	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
	
}
