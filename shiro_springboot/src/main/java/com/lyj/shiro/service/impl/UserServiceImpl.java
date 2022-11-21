package com.lyj.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyj.shiro.mapper.UserMapper;
import com.lyj.shiro.entity.User;
import com.lyj.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUSerInfoByName(String name) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public List<String> getUserRoleInfo(String userName) {
        return userMapper.getUserRoleInfo(userName);
    }

    @Override
    public List<String> getPermissionsInfo(List<String> roles) {
        return userMapper.getPermissionsInfo(roles);
    }
}
