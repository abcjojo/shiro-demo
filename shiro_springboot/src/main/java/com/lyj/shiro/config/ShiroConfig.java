package com.lyj.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.lyj.shiro.realm.MyRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    // 配置SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        // 1、创建defaultWebSecurityManager对象
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 2、创建加密对象，设置加密对象
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");  // 加密方式
        matcher.setHashIterations(3); // 迭代加密次数
        // 3、加密对象封装到myRealm中
        myRealm.setCredentialsMatcher(matcher);
        // 4、myRealm对象封装到manager中
        manager.setRealm(myRealm);
        manager.setRememberMeManager(rememberMeManager());
        // 设置缓存管理器
        manager.setCacheManager(getEhcacheManager());

        return manager;
    }

    // ehcache 缓存管理器
    private EhCacheManager getEhcacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        InputStream input = null;
        try {
            // 获取配置信息
            input = ResourceUtils.getInputStreamForPath("classpath:ehcache/ehcache-shiro.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 添加缓存管理器
        CacheManager cacheManager = new CacheManager(input);
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    // 创建cookie的管理对象
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(rememberMeSimpleCookie());
        manager.setCipherKey("1234567890987654".getBytes());
        return manager;
    }

    // cookie属性设置
    public SimpleCookie rememberMeSimpleCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置跨域
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }

    // 配置多个realm
//    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager2() {
        // 创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 创建认证对象
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        // 设置认证策略
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        manager.setAuthenticator(authenticator);
        List<Realm> list = new ArrayList<>();
        // 模拟自定义多个realm
        list.add(myRealm);
        list.add(myRealm);
        list.add(myRealm);
        manager.setRealms(list);
        return manager;
    }

    // 配置shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition filter = new DefaultShiroFilterChainDefinition();
        // anon：无需认证即可访问。  authc：需要认证才可访问。  user：点击“记住我”功能可访问。
        // 设置不认证可访问的资源
        filter.addPathDefinition("/myController/userLogin", "anon");
        filter.addPathDefinition("/myController/login", "anon");
        // 配置登出路径
        filter.addPathDefinition("/logout", "logout");
        // 设置需要登录认证的拦截范围
        filter.addPathDefinition("/**", "authc");
        filter.addPathDefinition("/**", "user");

        return filter;
    }

    // shiro的标签信息
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
