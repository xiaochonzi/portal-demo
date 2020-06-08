package com.example.portal.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {
    private Integer userId;
    private String userName;
    private String password;
    private Boolean disable;
    private RoleModel roleModel;
}
