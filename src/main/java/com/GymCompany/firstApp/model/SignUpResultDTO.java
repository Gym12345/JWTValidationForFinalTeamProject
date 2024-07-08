package com.GymCompany.firstApp.model;

public class SignUpResultDTO {
	   private boolean success;

	    private int code;

	    private String msg;
	    public SignUpResultDTO() {
			// TODO Auto-generated constructor stub
		}
		public SignUpResultDTO(boolean success, int code, String msg) {
			super();
			this.success = success;
			this.code = code;
			this.msg = msg;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		@Override
		public String toString() {
			return "SignUpResultDTO [success=" + success + ", code=" + code + ", msg=" + msg + "]";
		}
	    
}
