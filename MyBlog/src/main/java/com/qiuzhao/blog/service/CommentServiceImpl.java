package com.qiuzhao.blog.service;

import com.qiuzhao.blog.dao.CommentDao;
import com.qiuzhao.blog.domain.Comment;
import com.qiuzhao.blog.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/13
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> getListCommentById(Integer id) {
        return commentDao.getListCommentById(id);
    }

    @Override
    public List<Comment> listCommentByBlogIdAndParentCommentIsNull(Integer id) {
        // 获取所有父节点
        List<Comment> comments = commentDao.listCommentByBlogIdAndParentCommentIsNull(id);
        return eachComment(comments);
    }

    @Override
    public Comment getComment(Integer id) {
        return commentDao.getComment(id);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public boolean saveComment(Comment comment) {
        Integer parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentDao.getComment(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        for(Comment comment: commentsView){
            // 将父节点的replyComment设置上
            List<Comment> replyComments = commentDao.getListCommentById(comment.getId());
            comment.setReplyComments(replyComments);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                // 设置子代的父comment
                reply1.setParentComment(comment);
                // 设置其replyComment
                reply1.setReplyComments(commentDao.getListCommentById(reply1.getId()));
                //循环迭代，找出子代，存放在tempReplys中,flag为true就是把子一代评论加进去
                flag = true;
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    private boolean flag = true;
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        if(flag){
            //顶节点添加到临时存放集合
            tempReplys.add(comment);
        }
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                // 设置其设置父Comment
                reply.setParentComment(comment);
                reply.setReplyComments(commentDao.getListCommentById(reply.getId()));
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    // flag为false，防止循环时，再次加入到tempReplys中
                    flag = false;
                    recursively(reply);
                }
            }
        }
    }
}
