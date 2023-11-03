package com.ning.service.impl;
import com.ning.HttpStatusCode;
import com.ning.ResponseResult;
import com.ning.config.MinioUtil;
import com.ning.mapper.UserMapper;
import com.ning.service.UserService;
import com.ning.user.entity.User;
import com.ning.user.query.UserQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final static Object lock=new Object();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private MinioUtil minioUtil;
    /**
     * 根据用户id获取用户信息
     * */
    @Override
    public ResponseResult getUserById(Integer id) {
        User user=new User();
        user.setId(id);
        User user1=userMapper.getUserByCondition(user);
        if(user1==null){
            return ResponseResult.fail(HttpStatusCode.USER_NOT_EXIST);
        }
        UserQuery userQuery=new UserQuery();
        BeanUtils.copyProperties(user1,userQuery);
        return ResponseResult.success(userQuery);
    }
    /**
     * 用户登录业务层
     * */
    @Override
    public ResponseResult login(UserQuery userQuery)  {
        //判断该用户是否存在，先检测状态，后进行密码对比，反之则泽然。
        User user=userMapper.getUserByAccount(userQuery.getAccount());
        System.out.println(user);
        if(user!=null){
            //检测用户账号状态是否为禁止
            if(user.getStatus()==1){
                return ResponseResult.fail(HttpStatusCode.USER_FORBIDDEN);
            }
            //将用户密码与数据库密码进行对比。
            String passSalt = user.getPassSalt();
            String password= DigestUtils.md5DigestAsHex((passSalt+userQuery.getPassword()).getBytes());
            System.out.println("password1="+password+"\r\npassword2="+user.getPassword());
            if(password.equals(user.getPassword())){
                String token= UUID.randomUUID().toString();
                BeanUtils.copyProperties(user,userQuery);
                userQuery.setToken(token);
                redisTemplate.opsForValue().set(user.getId()+"",token);
                redisTemplate.delete(userQuery.getAccount());
                System.out.println(user);
                return ResponseResult.success(userQuery);
            }
            return ResponseResult.fail(HttpStatusCode.PASSWORD_ERROR);
        }
        return ResponseResult.fail(HttpStatusCode.USER_NOT_EXIST);
    }
    /**
     * 用户注册业务层
     * */
    @Override
    public ResponseResult register(UserQuery userQuery) {
        //验证该账号是否已经注册
        User userVo=new User();
        userVo.setAccount(userQuery.getAccount());
        User user=userMapper.getUserByCondition(userVo);
        if(user!=null){
            return ResponseResult.fail(HttpStatusCode.ACCOUNT_REGISTER);
        }
        //验证码是否正确,正确则进行下一步
        String code = redisTemplate.opsForValue().get(userQuery.getAccount());
        if(code==null||!code.equals(userQuery.getCode())){
            return ResponseResult.fail(HttpStatusCode.CODE_ERROR);
        }
        String passSalt = UUID.randomUUID().toString();
        //将明文密码与密码盐值结合进行md5加密.
        String password= DigestUtils.md5DigestAsHex((passSalt+userQuery.getPassword()).getBytes());
        //将User实体包剩下的参数进行填充，并且插入数据库中，并且清除redis中的验证码信息，之后发送响应码给用户
        User registerUser=new User();
        BeanUtils.copyProperties(userQuery,registerUser);
        registerUser.setId(getUserId());
        registerUser.setPassword(password);
        registerUser.setPassSalt(passSalt);
        registerUser.setStatus(0);
        registerUser.setIsDeleted(0);
        registerUser.setUpdateTime(LocalDateTime.now());
        registerUser.setCreateTime(LocalDateTime.now());
        int row=userMapper.register(registerUser);
        if(row>0){
            redisTemplate.delete(userQuery.getAccount());
            return ResponseResult.success(HttpStatusCode.REGISTER_SUCCESS);
        }
        return ResponseResult.fail(HttpStatusCode.REGISTER_ERROR);
    }
    @Override
    public ResponseResult deleteUserById(Integer id) {
        int row = userMapper.deleteUserById(id);
        if(row>0){
            return ResponseResult.success(HttpStatusCode.DELETE_SUCCESS);
        }
        return ResponseResult.success(HttpStatusCode.DELETE_ERROR);
    }
    /**
     * 上传用户头像
     * */
    @Override
    public ResponseResult uploadAvatar(MultipartFile file) {
        String fileUrl = minioUtil.uploadAvatar(file);
        return ResponseResult.success(fileUrl);
    }
    /**
     * 发送六位数验证码
     * */
    @Override
    public ResponseResult sendCode(String account) {
        String code = getCode();
        System.out.println("账号("+account+")验证码为:"+code);
        redisTemplate.opsForValue().set(account,code,60*10,TimeUnit.SECONDS);
        return ResponseResult.success(HttpStatusCode.CODE_SEND_SUCCESS);
    }
    /**
     * 更新用户密码
     * */
    @Override
    public ResponseResult updatePwdByAccount(UserQuery userQuery) {
        //判断该用户是否存在
        User userVo=new User();
        userVo.setAccount(userQuery.getAccount());
        User user=userMapper.getUserByCondition(userVo);
        if(user==null){
            return ResponseResult.fail(HttpStatusCode.USER_NOT_EXIST);
        }
        //验证码是否正确
        String code = redisTemplate.opsForValue().get(userQuery.getAccount());
        if(code==null||!code.equals(userQuery.getCode())){
            return ResponseResult.fail(HttpStatusCode.CODE_ERROR);
        }
        //开始更新用户密码，并且清除redis中的验证码信息，之后发送响应码给用户
        User user1=new User();
        BeanUtils.copyProperties(userQuery,user1);
        String passSalt = user.getPassSalt();
        String password= DigestUtils.md5DigestAsHex((passSalt+userQuery.getPassword()).getBytes());
        user1.setPassword(password);
        user1.setUpdateTime(LocalDateTime.now());
        int row = userMapper.updateUserByIdOrAccount(user1);
        if(row>0){
            redisTemplate.delete(userQuery.getAccount());
            redisTemplate.delete(user.getId()+"");
            return ResponseResult.success(HttpStatusCode.UPDATE_SUCCESS);
        }
        return ResponseResult.fail(HttpStatusCode.UPDATE_ERROR);
    }
    /**
     * 生成六位数随机验证码
     * */
    public static String getCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
    /**
     * 获取用户id
     * */
    private final static String USER_ID="userId";
    private Integer getUserId(){
        Integer userId = Integer.valueOf(redisTemplate.opsForValue().get(USER_ID));
        synchronized (lock){
            userId++;
            redisTemplate.opsForValue().set(USER_ID,userId+"");
        }
        return userId;
    }

}
