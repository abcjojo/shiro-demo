package com.lyj.shiro.service;

import com.lyj.shiro.entity.User;
import java.util.List;

public interface UserService {

    User getUSerInfoByName(String name);

    List<String> getUserRoleInfo(String userName);

    List<String> getPermissionsInfo(List<String> roles);
}
