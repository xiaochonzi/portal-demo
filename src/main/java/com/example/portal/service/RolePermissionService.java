package com.example.portal.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.portal.mapper.RolePermissionMapper;
import com.example.portal.model.entity.Permission;
import com.example.portal.model.entity.RolePermission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {
    public List<Permission> findPermissionByRoleId(Integer roleId) {
        return this.baseMapper.findPermissionByRoleId(roleId);
    }
}
