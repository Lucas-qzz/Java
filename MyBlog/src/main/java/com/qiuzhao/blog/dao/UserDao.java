package com.qiuzhao.blog.dao;

import com.qiuzhao.blog.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    /**
     * @Author 小朝
     * @Description  检查用户
     * @Date 20:32 2020/3/7
     * @Param [username, password]
     * @return com.qiuzhao.blog.domain.User
     **/
    User checkUser(String username,String password);
}
