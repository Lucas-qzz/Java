package com.qiuzhao.blog.service;

import com.qiuzhao.blog.dao.UserDao;
import com.qiuzhao.blog.domain.User;
import com.qiuzhao.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/7
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        return userDao.checkUser(username, MD5Utils.code(password));
    }

}
