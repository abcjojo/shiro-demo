package com.lyj.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;


public class ShiroRun {

    public static void main(String[] args) {

        // 1、初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        // 2、获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 3、创建token对象，模拟用户名密码输入
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "z3");
        // 4、模拟登陆
        try {
            subject.login(token);
            // 没有报错就说明模拟登录成功
            System.out.println("登陆成功");
            // 判断是否有角色
            boolean hasRole = subject.hasRole("role1");
            System.out.println(hasRole);
            // 权限判断
            boolean permitted = subject.isPermitted("user:insert");
            System.out.println(permitted);
            // checkPermission 方法校验权限没有返回值，如果没有权限会抛异常， isPermitted不会抛异常，会返回false
            subject.checkPermission("user:delete");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }
}
