package com.lyj.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyj.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT r.name\n" +
            "FROM user u\n" +
            "left join user_role ur on u.id = ur.uid\n" +
            "left join role r on ur.rid = r.id\n" +
            "where u.name = #{userName}")
    List<String> getUserRoleInfo(@Param("userName") String userName);

    @Select({
            "<script>",
            "SELECT p.info\n" +
            "FROM permissions p\n" +
            "left join role_ps rp on p.id = rp.pid\n" +
                    "left join role r on rp.rid = r.id\n" +
                    "where r.`name` in \n" +
                    "<foreach collection='roles' item='name' open='(' separator=','  close=')' >" +
                    "#{name}" +
                    "</foreach>",
            "</script>"
    })
    List<String> getPermissionsInfo(@Param("roles") List<String> roles);
}
