package com.lyj.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthenticatingRealm {

    /**
     *  自定义的认证登录方法，shiro的login方法会在底层调用该类的认证方法进行认证
     *  需要配置自定义的realm生效，在ini文件或springboot中配置
     *  该方法只是获取进行对比的信息， 认证逻辑还是按照shiro底层认证逻辑完成
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1、获取身份信息
        String principal = token.getPrincipal().toString();
        System.out.println("principal = " + principal);
        // 2、获取凭证信息
        String password = new String((char[]) token.getCredentials());
        System.out.println("password = " + password);

        // 模拟数据库查询密码
        if (principal.equals("zhangsan")) {
            // 加盐3次加密后在数据库中存储的密码
            String pass = "7174f64b13022acd3c56e2781e098a5f";
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    token.getPrincipal(),
                    pass,
                    ByteSource.Util.bytes("salt"),
                    token.getPrincipal().toString()
            );
            return info;
        }

        return null;
    }


}
