package com.example.portal.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("permission")
public class Permission extends BaseEntity {
    private String permissionName;
    private Integer parentId;
    private Integer sortIndex;
}
