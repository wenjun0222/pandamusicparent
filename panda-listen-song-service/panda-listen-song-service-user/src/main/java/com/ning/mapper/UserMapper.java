package com.ning.mapper;


import com.ning.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户的id,user,age,gender,status,is_deleted,account等状况进行用户的查询
     * */
    User getUserByCondition(User user);
    /**
     * 可以批量增加用户信息到数据库中
     * */
    int register(User user);
    /**
     * 可以根据用户的id,account,is_deleted等情况修改用户的信息
     * */
    int updateUserByIdOrAccount(User user);
    /**
    *删除用户信息
     * */
    int deleteUserById(long id);
    /**获取用户列表(也可根据用户名称进行模糊查询)
     */
    List<User> getUserList(String username);
    User getUserByAccount(String account);
}
