package com.example.portal.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.portal.mapper.PermissionMapper;
import com.example.portal.model.entity.Permission;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {
}
