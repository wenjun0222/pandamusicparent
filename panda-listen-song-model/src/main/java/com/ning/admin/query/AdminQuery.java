package com.ning.admin.query;

import com.ning.admin.entity.Admin;

import java.io.Serializable;

public class AdminQuery extends Admin implements Serializable{
    /**
     * 验证码
     * */
    private String code;
    /**
     * token值
     * */
    private String token;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AdminQuery{" +
                super.toString()+
                ", code='" + code + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
