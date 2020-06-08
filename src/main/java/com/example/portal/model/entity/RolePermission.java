package com.example.portal.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_permission")
public class RolePermission extends BaseEntity {
    private Integer roleId;
    private Integer permissionId;
}
