package com.example.portal.model.core;

import com.example.portal.model.exception.Code;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -3948389268046368059L;

    private Integer code;
    private String message;
    private T data;

    public Response() {
    }

    public Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response ok() {
        return ok(null);
    }

    public static <T> Response ok(T data) {
        return new Response(200, "ok", data);
    }

    public static Response failure(String message) {
        return failure(500, message);
    }

    public static Response failure(Integer code, String message) {
        return new Response(code, message, null);
    }

    public static Response failure(Code code) {
        return failure(code.getCode(), code.getMessage());
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
