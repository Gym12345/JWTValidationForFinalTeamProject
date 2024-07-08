package com.GymCompany.firstApp.Response;

public enum CommonResponse {
	 SUCCESS(1, "Success"), FAIL(0, "Fail");// The constructor CommonResponse(int, String)
	
	
	
	int code;
    String msg;
	private CommonResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
    
    
    
}
