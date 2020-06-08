package com.example.portal.security.auth;

import com.example.portal.model.enums.ErrorCode;
import com.example.portal.model.exception.BaseException;
import com.example.portal.utils.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtils.outJSON(response, new BaseException(ErrorCode.ACCESS_DENIED));
    }
}
