package com.example.portal.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;

public class BaseEntity {
    @TableId(value = "id", type = IdType.AUTO)
    public Integer id;

    @TableField("disable")
    @TableLogic(value = "1", delval = "0")
    public Boolean disable;

    @TableField("ctime")
    public Date ctime;

    public Integer getId() {
        return id;
    }

    public Boolean getDisable() {
        return disable;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
