package com.example.portal.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role")
public class Role extends BaseEntity {
    private String roleName;
    private Integer level;
}
