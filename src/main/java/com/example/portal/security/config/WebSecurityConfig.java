package com.example.portal.security.config;

import com.example.portal.model.properties.PortalProperties;
import com.example.portal.security.auth.*;
import com.example.portal.security.filters.DefaultAuthenticationEntryPoint;
import com.example.portal.security.filters.JwtAuthFilter;
import com.example.portal.service.CaptchaService;
import com.example.portal.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(PortalProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PortalProperties properties;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public CustomLoginFilter loginFilter() throws Exception {
        CustomLoginFilter customLoginFilter = new CustomLoginFilter();
        customLoginFilter.setAuthenticationManager(authenticationManagerBean());
        customLoginFilter.setCaptchaService(captchaService);
        customLoginFilter.setProperties(properties);
        customLoginFilter.setAuthenticationFailureHandler(new AuthFailureHandler());
        customLoginFilter.setAuthenticationSuccessHandler(new AuthSuccessHandler(redisService));
        return customLoginFilter;
    }

    public JwtAuthFilter jwtAuthFilter() throws Exception {
        JwtAuthFilter filter = new JwtAuthFilter(authenticationManagerBean(), new DefaultAuthenticationEntryPoint());
        filter.setRedisService(redisService);
        return filter;
    }

    public LogoutSuccessHandler logoutSuccessHandler() {
        LogoutSuccessHandler logoutHandler = new DefaultLogoutSuccessHandler(redisService);
        return logoutHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("captcha");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        registry.antMatchers("/index/ignoreUrl").permitAll();
        registry.anyRequest().authenticated();
        http.csrf().disable();

        http.logout().logoutSuccessHandler(logoutSuccessHandler());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtAuthFilter(), BasicAuthenticationFilter.class);// 这里可以放到logoutFilter之前
        http.exceptionHandling().accessDeniedHandler(new DefaultAccessDeniedHandler());
        http.exceptionHandling().authenticationEntryPoint(new DefaultAuthenticationEntryPoint());
    }
}
