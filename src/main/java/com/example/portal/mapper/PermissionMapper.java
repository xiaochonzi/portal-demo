package com.example.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.portal.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
