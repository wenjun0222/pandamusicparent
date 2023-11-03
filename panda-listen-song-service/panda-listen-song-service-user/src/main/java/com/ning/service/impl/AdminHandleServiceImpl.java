package com.ning.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ning.HttpStatusCode;
import com.ning.ResponseResult;
import com.ning.SearchCondition;
import com.ning.mapper.UserMapper;
import com.ning.service.AdminHandleService;
import com.ning.user.entity.User;
import com.ning.user.query.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminHandleServiceImpl implements AdminHandleService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    /**
     * 获取用户列表数据，每页4条数据。
     * */
    @Override
    public ResponseResult getUserList(SearchCondition searchCondition) {
        PageHelper.startPage(searchCondition.getCurrentPage(),4);
        System.out.println(searchCondition);
        List<User> userList = userMapper.getUserList(searchCondition.getName());
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        return ResponseResult.success(userPageInfo);
    }
    /**
     * 更新用户信息
     * */
    @Override
    public ResponseResult updateUser(UserQuery userQuery) {
        System.out.println(userQuery);
        //验证码是否正确
        String code = redisTemplate.opsForValue().get(userQuery.getAccount());
        if(code==null||!code.equals(userQuery.getCode())){
            return ResponseResult.fail(HttpStatusCode.CODE_ERROR);
        }
        //判断该用户是否存在
        User userVo=new User();
        userVo.setAccount(userQuery.getAccount());
        User user=userMapper.getUserByCondition(userVo);
        if(user==null){
            return ResponseResult.fail(HttpStatusCode.USER_NOT_EXIST);
        }
        User user1=new User();
        BeanUtils.copyProperties(userQuery,user1);
        //判断是否要修改密码
        if(userQuery.getPassword()!=null && !userQuery.getPassword().equals("")){
            String passSalt = user.getPassSalt();
            System.out.println(passSalt+userQuery.getPassword());
            String password= DigestUtils.md5DigestAsHex((passSalt+userQuery.getPassword()).getBytes());
            System.out.println(password);
            user1.setPassword(password);
        }
        user1.setUpdateTime(LocalDateTime.now());
        int row = userMapper.updateUserByIdOrAccount(user1);
        if(row>0){
            redisTemplate.delete(userQuery.getAccount());
            return ResponseResult.success();
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);
    }
    /**
     * 修改用户状态(下架，恢复权限等)
     * */
    @Override
    public ResponseResult updateUserStatus(UserQuery userQuery) {
        //判断该用户账号是否存在
        User userVo=new User();
        userVo.setId(userQuery.getId());
        User userByCondition = userMapper.getUserByCondition(userVo);
        if(userByCondition==null){
            return ResponseResult.fail(HttpStatusCode.USER_NOT_EXIST);
        }
        //开始修改用户状态status
        User user=new User();
        BeanUtils.copyProperties(userQuery,user);
        user.setUpdateTime(LocalDateTime.now());
        int row= userMapper.updateUserByIdOrAccount(user);
        if(row>0){
            return ResponseResult.success();
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);
    }
}
