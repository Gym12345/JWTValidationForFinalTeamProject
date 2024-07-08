package com.GymCompany.firstApp.model;


public class SignInResultDTO extends SignUpResultDTO { //토큰 함유

    private String token;

    public SignInResultDTO() {
    }

    public SignInResultDTO(boolean success, int code, String msg, String token) {
        super(success, code, msg);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SignInResultDto{" +
                "token='" + token + '\'' +
                ", success=" + isSuccess() +
                ", code=" + getCode() +
                ", msg='" + getMsg() + '\'' +
                '}';
    }

    // Static Builder Class
    public static class Builder {
        private boolean success;
        private int code;
        private String msg;
        private String token;

        public Builder() {
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public SignInResultDTO build() {
            return new SignInResultDTO(success, code, msg, token);
        }
    }
}
