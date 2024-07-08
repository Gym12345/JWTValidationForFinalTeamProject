package com.GymCompany.firstApp.model;


public class EntryPointErrorResponse {

    private String msg;

   
    public EntryPointErrorResponse() {
    }

  
    public EntryPointErrorResponse(String msg) {
        this.msg = msg;
    }

 
    public String getMsg() {
        return msg;
    }

   
    public void setMsg(String msg) {
        this.msg = msg;
    }

    // toString method
    @Override
    public String toString() {
        return "EntryPointErrorResponse{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
