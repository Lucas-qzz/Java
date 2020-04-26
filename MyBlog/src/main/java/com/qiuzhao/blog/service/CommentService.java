package com.qiuzhao.blog.service;

import com.qiuzhao.blog.domain.Comment;

import java.util.List;

public interface CommentService {
    /**
     * @date 2020/3/13 12:32
     * @author 小朝
     * @Description //根据blogId获取评论,而且没有父节点评论
     * @param id blog id
     * @return java.util.List<com.qiuzhao.blog.domain.Comment>
     **/
    List<Comment> listCommentByBlogIdAndParentCommentIsNull(Integer id);

    /**
     * @date 2020/3/13 12:35
     * @author 小朝
     * @Description //保存comment
     * @param comment 值
     * @return boolean
     **/
    boolean saveComment(Comment comment);

    /**
     * @date 2020/3/13 12:50
     * @author 小朝
     * @Description //根据评论的id获取comment
     * @param id comment的id
     * @return com.qiuzhao.blog.domain.Comment
     **/
    Comment getComment(Integer id);

    /**
     * @date 2020/3/13 16:01
     * @author 小朝
     * @Description //获取blog的子评论
     * @param id blog id
     * @return java.util.List<com.qiuzhao.blog.domain.Comment>
     **/;
    List<Comment> getListCommentById(Integer id);
}
