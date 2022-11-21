package com.lyj.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/myController")
public class MyController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 登录验证权限
    @RequiresPermissions("user:delete")
    @GetMapping("/userLoginPermission")
    @ResponseBody
    public String userLoginPermission() {
        System.out.println("登录认证验证权限");
        return "验证角色权限成功";
    }

    // 登录验证角色
    @RequiresRoles("admin")
    @GetMapping("/userLoginRoles")
    @ResponseBody
    public String userLoginRoles() {
        System.out.println("登录认证验证角色");
        return "验证角色成功";
    }

    // 登录记住我选项验证接口  localhost:8080/myController/userLoginRm
    @GetMapping("/userLoginRm")
    public String userLoginRm(HttpSession session) {
        session.setAttribute("user", "rememberMe");
        return "main";
    }


    // 测试地址  localhost:8080/myController/userLogin?name=zhangsan&pwd=z3
    @GetMapping("/userLogin")
    public String userLogin(String name, String pwd,
                            @RequestParam(defaultValue = "false") boolean rememberMe,
                            HttpSession session) {
        // 获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 封装请求数据（用户名、密码）到token对象中
        AuthenticationToken token = new UsernamePasswordToken(name, pwd, rememberMe);
        // 调用login方法进行登录认证
        try {
            subject.login(token);
            session.setAttribute("user", token.getPrincipal().toString());
//            return "success";
            return "main";
        } catch (AuthenticationException e) {
            System.out.println("登录失败");
            e.printStackTrace();
            return "failed";
        }
    }
}
