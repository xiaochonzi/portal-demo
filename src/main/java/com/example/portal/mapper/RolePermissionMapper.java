package com.example.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.portal.model.entity.Permission;
import com.example.portal.model.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<Permission> findPermissionByRoleId(Integer roleId);
}
