package com.ning.service;

import com.ning.ResponseResult;
import com.ning.admin.entity.Admin;
import com.ning.admin.query.AdminQuery;

public interface AdminService {
    ResponseResult registerAdmin(AdminQuery adminQuery);
    ResponseResult setAdminStatus(Integer id, Integer status);
    ResponseResult login(AdminQuery adminQuery);
    ResponseResult sendCode(String account);
    ResponseResult updatePwd(AdminQuery adminQuery);
    ResponseResult getAdminList();
}
