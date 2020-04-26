package com.qiuzhao.blog.service;

import com.qiuzhao.blog.dao.BlogDao;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/10
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Override
    public Blog getBlog(Integer id) {
        return blogDao.getBlog(id);
    }

    @Override
    public List<Blog> listBlog(Blog blog) {
        return blogDao.listBlog(blog);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public boolean saveBlog(Blog blog) {
        return blogDao.saveBlog(blog);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public boolean updateBlog(Integer id, Blog blog) {
        return blogDao.updateBlog(id,blog);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public boolean deleteBlog(Integer id) {
        return blogDao.deleteBlog(id);
    }

    @Override
    public List<Blog> listBlogAboutRecommend() {
        return blogDao.listBlogAboutRecommend();
    }

    @Override
    public List<Blog> countByTypes(Integer id) {
        return blogDao.countByTypes(id);
    }

    @Override
    public List<Blog> searchBlogs(String query) {
        return blogDao.searchBlogs(query);
    }

    @Override
    public void updateBlogViews(Integer id) {
        blogDao.updateBlogViews(id);
    }

    @Override
    public List<String> selectYears() {
        return blogDao.selectYears();
    }

    @Override
    public List<Blog> selectByYear(String year) {
        return blogDao.selectByYear(year);
    }

    @Override
    public List<Blog> selectNewBlog() {
        return blogDao.selectNewBlog();
    }
}
