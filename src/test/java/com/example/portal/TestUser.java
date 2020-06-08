package com.example.portal;

import com.example.portal.model.entity.Permission;
import com.example.portal.model.entity.Role;
import com.example.portal.model.entity.RolePermission;
import com.example.portal.model.entity.User;
import com.example.portal.service.PermissionService;
import com.example.portal.service.RolePermissionService;
import com.example.portal.service.RoleService;
import com.example.portal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestUser extends BaseTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;

    @Test
    public void testInsert() {
        Role role = new Role();
        role.setLevel(1);
        role.setRoleName("admin");

        Permission permission = new Permission();
        permission.setParentId(0);
        permission.setSortIndex(1);
        permission.setPermissionName("query");

        roleService.save(role);
        permissionService.save(permission);
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(role.getId());
        rolePermission.setPermissionId(permission.getId());
        rolePermissionService.save(rolePermission);

        User user = new User();
        user.setUserName("admin");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setNickName("admin");
        user.setRoleId(role.getId());
        userService.save(user);
    }
}
