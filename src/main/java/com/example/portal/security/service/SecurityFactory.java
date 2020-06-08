package com.example.portal.security.service;

import com.example.portal.model.auth.AuthUser;
import com.example.portal.model.auth.RoleModel;
import com.example.portal.model.auth.UserModel;
import com.example.portal.model.entity.Permission;
import com.example.portal.model.entity.Role;
import com.example.portal.model.entity.User;
import com.example.portal.service.RolePermissionService;
import com.example.portal.service.RoleService;
import com.example.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityFactory {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    public AuthUser LoadAuthUser(String userName) {
        User user = userService.findByUserName(userName);
        if (user != null) {
            Role role = roleService.findRoleById(user.getRoleId());
            List<Permission> permissionList = rolePermissionService.findPermissionByRoleId(user.getRoleId());
            String roleName = null;
            if (role != null) {
                roleName = role.getRoleName();
            }
            List<String> permissions = new ArrayList<>();
            if (permissionList != null) {
                for (Permission permission : permissionList) {
                    permissions.add(permission.getPermissionName());
                }
            }
            RoleModel roleModel = new RoleModel(roleName, permissions);
            UserModel userModel = new UserModel(user.getId(), user.getUserName(), user.getPassword(), user.getDisable(), roleModel);
            AuthUser authUser = new AuthUser(userModel);
            return authUser;
        }
        return null;
    }

    public AuthUser getCurrentUser() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authUser;
    }
}
