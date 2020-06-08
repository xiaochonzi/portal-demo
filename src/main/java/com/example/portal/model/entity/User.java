package com.example.portal.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User extends BaseEntity {
    private String userName;
    private String password;
    private String nickName;
    private Boolean disable;
    private Integer roleId;
}
