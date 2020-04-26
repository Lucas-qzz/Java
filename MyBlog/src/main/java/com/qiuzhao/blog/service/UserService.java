package com.qiuzhao.blog.service;

import com.qiuzhao.blog.domain.User;


import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/7
 **/

public interface UserService {

    User checkUser(String username,String password);
}
