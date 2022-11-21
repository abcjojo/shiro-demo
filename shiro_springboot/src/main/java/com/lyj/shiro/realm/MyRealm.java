package com.lyj.shiro.realm;

import com.lyj.shiro.entity.User;
import com.lyj.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前登录用户的身份信息
        String userName = principalCollection.getPrimaryPrincipal().toString();
        // 获取该用户的角色列表
        List<String> roleList = userService.getUserRoleInfo(userName);
        System.out.println("当前登录用户角色列表roleList = " + roleList);
        // 获取用户权限列表
        List<String> permissions = userService.getPermissionsInfo(roleList);
        System.out.println("当前登录用户权限列表permissionList = " + permissions);
        // 1 创建对象， 封装当前登录用户的角色权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleList);
        info.addStringPermissions(permissions);
        // 返回认证信息
        return info;
    }

    // 自定义认证登录方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取当前登录用户信息
        String name = authenticationToken.getPrincipal().toString();
        // 查询用户
        User user = userService.getUSerInfoByName(name);
        // 封装数据返回
        if (null != user) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    user.getPwd(),
                    ByteSource.Util.bytes("salt"),
                    name
            );
            return info;
        }
        return null;
    }
}
