package com.example.portal.security.service;

import com.example.portal.model.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SecurityFactory securityFactory;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AuthUser authUser = securityFactory.LoadAuthUser(userName);
        return authUser;
    }
}
