package com.example.portal.security.auth;

import com.example.portal.model.consts.SecurityConstant;
import com.example.portal.model.core.Response;
import com.example.portal.utils.RedisService;
import com.example.portal.utils.ResponseUtils;
import com.example.portal.utils.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    private RedisService redisService;

    public DefaultLogoutSuccessHandler(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstant.TOKEN);
        if (StringUtils.isNullOrEmpty(token)) {
            token = request.getParameter(SecurityConstant.TOKEN);
        }
        if (!StringUtils.isNullOrEmpty(token)) {
            redisService.del(token);
        }
        ResponseUtils.outJSON(response, Response.ok());
    }
}
