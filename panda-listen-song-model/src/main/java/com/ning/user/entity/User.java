package com.ning.user.entity;


import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 用户信息表(Users)实体类
 */
public class User implements Serializable {
    private static final long serialVersionUID = 597055966144532186L;
    /**
     * 用户唯一id
     */
    private Integer id;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户名字
     */
    private String username;
    /**
     * 用户密码盐值
     */
    private String passSalt;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户头像地址
     *
     */
    private String avatarPath;
    /**
     * 创建时间
     * */
    private LocalDateTime createTime;
    /**
     * 更新时间
     * */
    private LocalDateTime updateTime;
    /**
     * 创建者
     * */
    private String creator;
    /**
     * 更新者
     * */
    private String editor;
    /**
     * 用户状态,0为正常，1为禁用
     */
    private Integer status;
    /**
     * 0未删除，1已删除
     */
    private Integer isDeleted;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", passSalt='" + passSalt + '\'' +
                ", gender='" + gender + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creator='" + creator + '\'' +
                ", editor='" + editor + '\'' +
                ", status=" + status +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

