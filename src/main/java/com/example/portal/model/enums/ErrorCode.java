package com.example.portal.model.enums;

import com.example.portal.model.exception.Code;

public enum ErrorCode implements Code {
    USER_DONT_EXISTS(10001, "用户不存在"),
    PASSWORD_DONT_MATCH(10002, "用户名或密码不匹配"),
    CAPTCHA_EXPIRE(10003, "验证码过期"),
    CAPTCHA_ERROR(10004, "验证码不匹配"),
    AUTH_FAILURE(10005, "认证失败"),
    TOKEN_EXPIRE(10006, "登录已经过期, 请重新登录"),
    ACCESS_DENIED(10007, "无权限访问"),
    ;
    Integer code;
    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
