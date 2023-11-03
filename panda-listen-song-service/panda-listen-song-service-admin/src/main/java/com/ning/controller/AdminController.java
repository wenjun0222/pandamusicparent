package com.ning.controller;
import com.ning.ResponseResult;
import com.ning.admin.query.AdminQuery;
import com.ning.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping
public class AdminController {
    @Autowired
    private AdminService adminService;
    /**
     * 注册管路员账号
     * */
    @PostMapping("registerAdmin")
    public ResponseResult registerAdmin(@RequestBody AdminQuery adminQuery){
        return adminService.registerAdmin(adminQuery);
    }
    /**
     * 禁止管理员账号权限
     * */
    @GetMapping("forbidAdmin/{id}")
    public ResponseResult forbidAdmin(@PathVariable Integer id){
        return adminService.setAdminStatus(id,1);
    }
    /**
     * 修改管理员密码
     * */
    @PostMapping("updatePwd")
    public ResponseResult updatePwdById(@RequestBody AdminQuery adminQuery){
        return adminService.updatePwd(adminQuery);
    }
    /**
     * 恢复管理员账号权限
     * */
    @GetMapping("recoverAdmin/{id}")
    public ResponseResult recoverAdmin(@PathVariable Integer id){
        return adminService.setAdminStatus(id,0);
    }
    /**
     * 管理员登录
     * */
    @PostMapping("login")
    public ResponseResult login(@RequestBody AdminQuery adminQuery){
        return adminService.login(adminQuery);
    }
    /**
     * 发送验证码
     * */
    @GetMapping("sendCode/{account}")
    public ResponseResult sendCode(@PathVariable String account){
        return adminService.sendCode(account);
    }
    /**
     * 获取管理员列表数据
     * */
    @GetMapping("getAdminList")
    public ResponseResult getAdminList(){
        return adminService.getAdminList();
    }
}
