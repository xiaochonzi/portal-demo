package com.example.portal.security.auth;

import com.example.portal.model.enums.ErrorCode;
import com.example.portal.model.exception.BaseException;
import com.example.portal.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败
 */
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        BaseException ex = null;
        if (e instanceof BadCredentialsException) {
            ex = new BaseException(ErrorCode.PASSWORD_DONT_MATCH);
        }
        if (ex == null) {
            ex = new BaseException(e.getMessage());
        }
        ResponseUtils.outJSON(httpServletResponse, ex);
    }
}
