package com.ning.controller;

import com.ning.ResponseResult;
import com.ning.service.UserService;
import com.ning.user.entity.User;

import com.ning.user.query.UserQuery;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@Api
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 用户登录
     * */
    @PostMapping("login")
    public ResponseResult login(@RequestBody UserQuery userQuery){
        return userService.login(userQuery);
    }
    /**
     * 用户注册
     * */
    @PostMapping("register")
    public ResponseResult register(@RequestBody UserQuery userQuery){
        return userService.register(userQuery);
    }
    @GetMapping("getUserById/{userId}")
    public ResponseResult<User> getUserById(@PathVariable Integer userId){
        return userService.getUserById(userId);
    }
    /**
     * 修改用户密码
     * */
    @PostMapping("updatePwdByAccount")
    public ResponseResult updatePwdByAccount(@RequestBody UserQuery userQuery){
        return userService.updatePwdByAccount(userQuery);
    }
    /**
     * 用户头像上传
     * */
    @PostMapping("uploadAvatar")
    public ResponseResult uploadAvatar(MultipartFile file){
        return userService.uploadAvatar(file);
    }
    /**
     * 根据用户账号发送验证码
     * */
    @GetMapping("sendCode/{account}")
    public ResponseResult sendCode(@PathVariable String account ){
        return userService.sendCode(account);
    }
}
