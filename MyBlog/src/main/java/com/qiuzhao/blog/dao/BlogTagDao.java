package com.qiuzhao.blog.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/11
 **/
@Repository
public interface BlogTagDao {
    boolean saveBlogAndTag(Integer bid,Integer tid);
    boolean deleteBlogAndTag(Integer id);
    List<Integer> getPartOfListTag(Integer id);
    List<Integer> getPartOfListBlog(Integer id);
}
