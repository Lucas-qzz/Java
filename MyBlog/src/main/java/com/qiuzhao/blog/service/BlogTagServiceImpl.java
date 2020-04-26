package com.qiuzhao.blog.service;

import com.qiuzhao.blog.dao.BlogTagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/11
 **/
@Service
public class BlogTagServiceImpl implements BlogTagService {
    @Autowired
    private BlogTagDao blogTagDao;
    @Override
    public boolean saveBlogAndTag(Integer bid, Integer tid) {
        return blogTagDao.saveBlogAndTag(bid,tid);
    }

    @Override
    public boolean deleteBlogAndTag(Integer id){
        return blogTagDao.deleteBlogAndTag(id);
    }

    @Override
    public List<Integer> getPartOfListTag(Integer id) {
        return blogTagDao.getPartOfListTag(id);
    }

    @Override
    public List<Integer> getPartOfListBlog(Integer id) {
        return blogTagDao.getPartOfListBlog(id);
    }
}
