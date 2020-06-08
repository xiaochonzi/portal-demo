package com.example.portal.model.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails {

    private UserModel userModel;
    private RoleModel roleModel;

    public AuthUser(UserModel userModel) {
        this.userModel = userModel;
        this.roleModel = userModel.getRoleModel();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (roleModel != null && roleModel.getPermissions() != null) {
            roleModel.getPermissions().forEach(p -> {
                authorityList.add(new SimpleGrantedAuthority(p));
            });
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.userModel.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userModel.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userModel.getDisable();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }
}
