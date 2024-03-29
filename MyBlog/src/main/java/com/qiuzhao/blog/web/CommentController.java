package com.qiuzhao.blog.web;

import com.qiuzhao.blog.domain.Comment;
import com.qiuzhao.blog.domain.User;
import com.qiuzhao.blog.service.BlogService;
import com.qiuzhao.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 小朝
 * @date 2020/3/13
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Integer blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogIdAndParentCommentIsNull(blogId));
        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user=(User)session.getAttribute("user");
        if(user !=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }else {
            comment.setAdminComment(false);
        }
        Integer blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }
}
