package com.qiuzhao.blog.service;

import java.util.List;

public interface BlogTagService {

    boolean saveBlogAndTag(Integer bid,Integer tid);
    boolean deleteBlogAndTag(Integer id);
    List<Integer> getPartOfListTag(Integer id);
    List<Integer> getPartOfListBlog(Integer id);
}
