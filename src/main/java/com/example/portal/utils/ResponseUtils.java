package com.example.portal.utils;

import com.alibaba.fastjson.JSON;
import com.example.portal.model.core.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: ResponseUtils
 * @description:
 * @author: Chonzi.xiao
 * @create: 2019-08-19 09:55
 */
@Slf4j
public class ResponseUtils {

    public static void outJSON(HttpServletResponse response, Response message) {
        ServletOutputStream servletOutputStream = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            servletOutputStream = response.getOutputStream();
            servletOutputStream.write(JSON.toJSONString(message).getBytes("utf-8"));
            servletOutputStream.flush();
        } catch (Exception ex) {
            ;
        }
    }

    public static void outJSON(HttpServletResponse response, Object obj) {
        outJSON(response, Response.ok(obj));
    }

    public static void outJSON(HttpServletResponse response, Exception e) {
        outJSON(response, Response.failure(e.getMessage()));
    }

}
