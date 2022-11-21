package com.lyj.shiro.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class PermissionsException {

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedExp(Exception e) {
        System.out.println("异常捕获UnauthorizedException" + e.getMessage());
        return "无相关权限，请联系管理员！";
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationExp(Exception e) {
        System.out.println("异常捕获AuthorizationException" + e.getMessage());
        return "权限验证失败，请联系管理员！";
    }
}
