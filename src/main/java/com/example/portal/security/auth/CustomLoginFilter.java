package com.example.portal.security.auth;

import com.example.portal.model.exception.BaseException;
import com.example.portal.model.properties.PortalProperties;
import com.example.portal.service.CaptchaService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {


    private PortalProperties properties;

    private CaptchaService captchaService;

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            if (properties.getEnableCaptcha()) {
                try {
                    String captchaId = request.getHeader("captchaId");
                    String captchaCode = request.getParameter("captchaCode");
                    captchaService.verifyCaptcha(captchaId, captchaCode);
                } catch (BaseException e) {
                    throw new InternalAuthenticationServiceException(e.getMessage());
                }
            }

            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public void setProperties(PortalProperties properties) {
        this.properties = properties;
    }

}
