package com.ning.service.impl;

import com.ning.CodeUtils;
import com.ning.HttpStatusCode;
import com.ning.ResponseResult;
import com.ning.admin.entity.Admin;
import com.ning.admin.query.AdminQuery;
import com.ning.mapper.AdminMapper;
import com.ning.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    /**
     * 注册管理员账号
     * */
    public ResponseResult registerAdmin(AdminQuery adminQuery) {
        //判断要注册的管理员中的账号与昵称是否已经存在。
        Admin admin1=adminMapper.getAdminByCondition(adminQuery);
        if(admin1!=null){
            return ResponseResult.fail(HttpStatusCode.NAME_EXIST);
        }
        //判断该账号是否已经被注册
        Admin adminByAccount = adminMapper.getAdminByCondition(adminQuery);
        if(adminByAccount!=null){
            redisTemplate.delete(adminQuery.getAccount());
            return ResponseResult.fail(HttpStatusCode.ACCOUNT_REGISTER);
        }
        //对比验证码是否正确
        String code = redisTemplate.opsForValue().get(adminQuery.getAccount());
        if((code==null) || (!adminQuery.getCode().equals(code)) ){
            return ResponseResult.fail(HttpStatusCode.CODE_ERROR);
        }
        Admin admin=new Admin();
        BeanUtils.copyProperties(adminQuery,admin);
        Integer id=getAdminId();
        //将密码与盐值集合进行md5加密
        String passSalt = UUID.randomUUID().toString();
        String password= DigestUtils.md5DigestAsHex((passSalt+adminQuery.getPassword()).getBytes());
        System.out.println(password);
        //开始往数据库中插入注册的管理员信息
        admin.setId(id);
        admin.setPassword(password);
        admin.setPassSalt(passSalt);
        admin.setType(1);
        admin.setStatus(0);
        int row=adminMapper.registerAdmin(admin);
        if(row>0){
            redisTemplate.delete(adminQuery.getAccount());
            return ResponseResult.success();
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);
    }
    /**
     * 修改管理员是否可登录标志status
     * */
    @Override
    public ResponseResult setAdminStatus(Integer id,Integer status) {
        //判断该管理员账号是否存在
        AdminQuery adminQuery1=new AdminQuery();
        adminQuery1.setId(id);
        Admin adminById = adminMapper.getAdminByCondition(adminQuery1);
        if(adminById==null){
            return ResponseResult.fail(HttpStatusCode.ADMIN_NOT_EXIST);
        }
        //修改管理员状态status
        AdminQuery adminQuery=new AdminQuery();
        adminQuery.setId(id);
        adminQuery.setStatus(status);
        adminMapper.updateAdmin(adminQuery);
        return ResponseResult.fail(HttpStatusCode.UPDATE_ERROR);
    }
    /**
     * 管理员登录
     * */
    @Override
    public ResponseResult login(AdminQuery adminQuery) {
        //获取数据库中是否存在该管理员账号
        Admin adminByAccount = adminMapper.getAdminByCondition(adminQuery);
        if(adminByAccount==null){
            return ResponseResult.fail(HttpStatusCode.ADMIN_NOT_EXIST);
        }
        //判断该账号是否被禁用
        if(adminByAccount.getStatus()==1){
            return ResponseResult.fail(HttpStatusCode.USER_FORBIDDEN);
        }
        //登录密码md5加密后与数据库密码进行对比
        String passSalt = adminByAccount.getPassSalt();
        String password= DigestUtils.md5DigestAsHex((passSalt+adminQuery.getPassword()).getBytes());
        if(!password.equals(adminByAccount.getPassword())){
            return ResponseResult.fail(HttpStatusCode.PASSWORD_ERROR);
        }
        //验证码与账号都正确，生成token值存入redis并且响应给前端。
        String token=UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(adminByAccount.getId()+"",token,12, TimeUnit.HOURS);
        BeanUtils.copyProperties(adminByAccount,adminQuery);
        adminQuery.setPassword(null);
        adminQuery.setToken(token);
        return ResponseResult.success(adminQuery);
    }
    /**
     * 发送验证码，十分钟内有效
     * */
    @Override
    public ResponseResult sendCode(String account) {
        String code = CodeUtils.getCode();
        redisTemplate.opsForValue().set(account,code+"",10,TimeUnit.MINUTES);
        System.out.println("账号["+account+"]验证码为："+code);
        return ResponseResult.success(code);
    }
    /**
     * 修改密码
     * */
    @Override
    public ResponseResult updatePwd(AdminQuery adminQuery) {
        //判断验证码
        String code = redisTemplate.opsForValue().get(adminQuery.getAccount());
        if((!adminQuery.getCode().equals(code)) || (code==null)){
            return ResponseResult.fail(HttpStatusCode.CODE_ERROR);
        }
        //管理员账号是否存在//管理员账号是否存在
        Admin adminByAccount = adminMapper.getAdminByCondition(adminQuery);
        if(adminByAccount==null){
            return ResponseResult.fail(HttpStatusCode.ADMIN_NOT_EXIST);
        }
        //管理员是否可登录
        if(adminByAccount.getStatus()==1){
            return ResponseResult.fail(HttpStatusCode.USER_FORBIDDEN);
        }
        //开始修改密码步骤,成功删除该管理员账号在redis数据库存入的信息
        String passSalt = adminByAccount.getPassSalt();
        String password= DigestUtils.md5DigestAsHex((passSalt+adminQuery.getPassword()).getBytes());
        adminQuery.setPassword(password);
        int row = adminMapper.updateAdmin(adminQuery);
        if(row>0){
            redisTemplate.delete(adminQuery.getAccount());
            redisTemplate.delete(adminQuery.getId()+"");
            return ResponseResult.success(HttpStatusCode.UPDATE_PASSWORD_SUCCESS);
        }
        return ResponseResult.fail(HttpStatusCode.UPDATE_PASSWORD_ERROR);
    }
    /**
     * 获取全部的管理员数据
     * */
    @Override
    public ResponseResult getAdminList() {
        List<Admin> adminList = adminMapper.getAdminList();
        return ResponseResult.success(adminList);
    }
    /**
     * 注册时获取管理员id
     * */
    private final static  Object lock=new Object();
    private final static String KEY="adminId";
    private Integer getAdminId(){
        Integer adminId = Integer.valueOf(redisTemplate.opsForValue().get(KEY));
        synchronized (lock){
            adminId++;
        }
        redisTemplate.opsForValue().set(KEY,adminId+"");
        return adminId;
    }
}
