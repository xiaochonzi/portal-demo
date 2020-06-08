package com.example.portal.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.portal.mapper.RoleMapper;
import com.example.portal.model.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    public Role findRoleById(Integer roleId) {
        return this.getById(roleId);
    }
}
