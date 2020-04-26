package com.qiuzhao.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.domain.Type;
import com.qiuzhao.blog.service.BlogService;
import com.qiuzhao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/13
 **/
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Integer id,
                        @RequestParam(required = false, value = "blogPageNum") Integer blogPageNum,
                        Model model){
        if(blogPageNum==null || blogPageNum==0){
            blogPageNum = 1;
        }
        List<Type> types = typeService.listType();
        for(Type type : types){
            type.setBlogs(blogService.countByTypes(type.getId()));
        }
        if(id == -1){
            id = types.get(0).getId();
        }
        Blog blog = new Blog();
        blog.setType(typeService.getType(id));

        PageHelper.startPage(blogPageNum,5,"t_blog.update_time desc");
        List<Blog> blogs = blogService.listBlog(blog);
        PageInfo<Blog> blogPageInfo= new PageInfo<Blog>(blogs);


        model.addAttribute("types",types);
        model.addAttribute("blogPage",blogPageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
