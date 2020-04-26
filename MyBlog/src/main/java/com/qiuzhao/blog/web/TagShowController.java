package com.qiuzhao.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.domain.Tag;
import com.qiuzhao.blog.service.BlogService;
import com.qiuzhao.blog.service.BlogTagService;
import com.qiuzhao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/13
 **/
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Integer id, Model model){
        List<Tag> tags = tagService.listTag();
        if(id == -1){
            id = tags.get(0).getId();
        }

        for(Tag tag:tags){
            List<Integer> blogIds = blogTagService.getPartOfListBlog(tag.getId());
            List<Blog> blogs = new ArrayList<Blog>();
            for(Integer blogId:blogIds){
                blogs.add(blogService.getBlog(blogId));
            }
            tag.setBlogs(blogs);
        }

        List<Integer> blogIds = blogTagService.getPartOfListBlog(id);
        List<Blog> blogs = new ArrayList<Blog>();
        for(Integer blogId:blogIds){
            blogs.add(blogService.getBlog(blogId));
        }
        for(Blog blog:blogs){
            List<Integer> tagIds = blogTagService.getPartOfListTag(blog.getId());
            List<Tag> tagList = new ArrayList<Tag>();
            for(Integer tagId:tagIds){
                tagList.add(tagService.getTag(tagId));
            }
            blog.setTags(tagList);
        }


        model.addAttribute("blogs",blogs);
        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
