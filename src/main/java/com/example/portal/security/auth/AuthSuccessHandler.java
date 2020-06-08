package com.example.portal.security.auth;

import com.example.portal.model.auth.AuthUser;
import com.example.portal.model.core.Response;
import com.example.portal.utils.JwtTokenUtils;
import com.example.portal.utils.RedisService;
import com.example.portal.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private RedisService redisService;

    public AuthSuccessHandler(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        long expire = TimeUnit.DAYS.toMillis(7);
        String token = JwtTokenUtils.generateToken(authUser.getUsername(), expire);
        redisService.set(token, authUser.getUserModel(), expire, TimeUnit.MILLISECONDS);
        ResponseUtils.outJSON(httpServletResponse, Response.ok(token));
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
