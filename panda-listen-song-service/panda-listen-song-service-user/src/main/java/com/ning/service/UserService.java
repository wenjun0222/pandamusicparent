package com.ning.service;

import com.ning.ResponseResult;
import com.ning.user.entity.User;
import com.ning.user.query.UserQuery;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseResult<User> getUserById(Integer id);
    ResponseResult updatePwdByAccount(UserQuery userQuery);
    ResponseResult login(UserQuery userQuery);
    ResponseResult register(UserQuery userQuery);
    ResponseResult deleteUserById(Integer id);
    ResponseResult uploadAvatar(MultipartFile file);
    ResponseResult sendCode(String account);
}
