package com.ning.controller;

import com.ning.ResponseResult;
import com.ning.SearchCondition;
import com.ning.service.AdminHandleService;
import com.ning.user.entity.User;
import com.ning.user.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AdminHandleController {
    @Autowired
    private AdminHandleService adminHandleService;

    @PostMapping("getUserList")
    public ResponseResult getUserList(@RequestBody SearchCondition searchCondition) {
        return adminHandleService.getUserList(searchCondition);
    }
    /**
     * 修改用户信息
     */
    @PostMapping("updateUser")
    public ResponseResult updateUser(@RequestBody UserQuery userQuery) {
        return adminHandleService.updateUser(userQuery);
    }
    /**
     * 修改用户状态(下架，恢复权限等)
     * */
    @PostMapping("updateUserStatus")
    public ResponseResult updateUserStatus(@RequestBody UserQuery userQuery) {
        return adminHandleService.updateUserStatus(userQuery);
    }
}
