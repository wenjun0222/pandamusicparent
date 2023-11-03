package com.ning.admin.entity;

import java.io.Serializable;

/**
 * 管理员列表(Admins)实体类
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = -33850067300947211L;
    /**
     * 管理员id
     */
    private Integer id;
    /**
     * 管理员账号
     */
    private String account;
    /**
     * 管理员密码
     */
    private String password;
    /**
     * 管理员名字
     */
    private String adminName;
    /**
     * 1表示普通管理员，0表示超级管理员
     */
    private Integer type;
    /**
     * 管理员密码盐值
     */
    private String passSalt;
    /**
     * 管理员状态,0为正常，1为禁用
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", adminName='" + adminName + '\'' +
                ", type='" + type + '\'' +
                ", passSalt='" + passSalt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

