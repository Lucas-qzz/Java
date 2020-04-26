package com.qiuzhao.blog.service;

import com.qiuzhao.blog.domain.Blog;

import java.util.List;

/**
 * BlogService
 *
 * @author 小朝
 * @date 2020/3/10
 */
public interface BlogService {

    /**
     * @date 2020/3/10 19:28
     * @author 小朝
     * @Description //根据id查询博客
     * @param id 博客id
     * @return com.qiuzhao.blog.domain.Blog
     **/
    Blog getBlog(Integer id);

    /**
     * @date 2020/3/10 19:31
     * @author 小朝
     * @Description //返回blog列表
     * @param
     * @return java.util.List<com.qiuzhao.blog.domain.Blog>
     **/
    List<Blog> listBlog(Blog blog);

    /**
     * @date 2020/3/10 19:31
     * @author 小朝
     * @Description //新增blog
     * @param blog 新增的blog
     * @return boolean
     **/
    boolean saveBlog(Blog blog);

    /**
     * @date 2020/3/10 19:31
     * @author 小朝
     * @Description //根据id修改blog
     * @param id blog id
     * @param blog 修改内容
     * @return boolean
     **/
    boolean updateBlog(Integer id, Blog blog);

    /**
     * @date 2020/3/10 19:31
     * @author 小朝
     * @Description //根据id删除blog
     * @param id blog id
     * @return boolean
     **/
    boolean deleteBlog(Integer id);

    /**
     * @date 2020/3/12 13:37
     * @author 小朝
     * @Description //查询推荐的blog
     * @param
     * @return java.util.List<com.qiuzhao.blog.domain.Blog>
     **/
    List<Blog> listBlogAboutRecommend();

    /**
     * @date 2020/3/12 14:22
     * @author 小朝
     * @Description //查询该分类有多少篇blog
     * @param id 分类的id
     * @return java.lang.Integer
     **/
    List<Blog> countByTypes(Integer id);

    /**
     * @date 2020/3/12 15:36
     * @author 小朝
     * @Description //根据输入的条件进行搜索
     * @param query 输入的条件
     * @return java.util.List<com.qiuzhao.blog.domain.Blog>
     **/
    List<Blog> searchBlogs(String query);

    /**
     * @date 2020/3/13 19:16
     * @author 小朝
     * @Description //控制每次访问，浏览次数加一
     * @param id blog的id
     * @return void
     **/
    void updateBlogViews(Integer id);

    /**
     * @date 2020/3/14 12:27
     * @author 小朝
     * @Description //查询所有blog的年份
     * @param
     * @return java.util.List<java.lang.String>
     **/
    List<String> selectYears();

    /**
     * @date 2020/3/14 12:30
     * @author 小朝
     * @Description //查询每个年份的blog
     * @param year 年份
     * @return java.util.List<com.qiuzhao.blog.domain.Blog>
     **/
    List<Blog> selectByYear(String year);

    /**
     * @date 2020/3/14 13:36
     * @author 小朝
     * @Description //查询最新blog
     * @param
     * @return java.util.List<com.qiuzhao.blog.domain.Blog>
     **/
    List<Blog> selectNewBlog();
}
