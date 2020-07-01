package com.example.portal.security.filters;

import com.example.portal.model.auth.AuthUser;
import com.example.portal.model.auth.UserModel;
import com.example.portal.model.consts.SecurityConstant;
import com.example.portal.model.enums.ErrorCode;
import com.example.portal.utils.JwtTokenUtils;
import com.example.portal.utils.RedisService;
import com.example.portal.utils.StringUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends BasicAuthenticationFilter {

    private RedisService redisService;

    public JwtAuthFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstant.TOKEN);
        if (StringUtils.isNullOrEmpty(token)) {
            token = request.getParameter(SecurityConstant.TOKEN);
        }
        if (StringUtils.isNullOrEmpty(token)){
            chain.doFilter(request, response);
            return;
        }
        try {

            UsernamePasswordAuthenticationToken authRequest = getAuthentication(token);
            authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            getAuthenticationEntryPoint().commence(request, response, e);
            return;
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = JwtTokenUtils.getUsernameFromToken(token);
        if (username == null) {
            throw new CredentialsExpiredException(ErrorCode.TOKEN_EXPIRE.getMessage());
        }
        UserModel userModel = redisService.get(token);
        if (userModel == null) {
            throw new CredentialsExpiredException(ErrorCode.TOKEN_EXPIRE.getMessage());
        }
        AuthUser authUser = new AuthUser(userModel);
        return new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
    }

    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
