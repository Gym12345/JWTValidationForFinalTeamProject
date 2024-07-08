package com.GymCompany.firstApp.model;

public class TempUserDTO {
	private String userId;
	private String userPw;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public TempUserDTO() {
		// TODO Auto-generated constructor stub
	}
	public TempUserDTO(String userId, String userPw) {
		super();
		this.userId = userId;
		this.userPw = userPw;
	}
	@Override
	public String toString() {
		return "TempUserDTO [userId=" + userId + ", userPw=" + userPw + "]";
	}
	
	
}
