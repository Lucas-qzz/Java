package com.qiuzhao.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

/**
 * @author: 小朝
 * @date: 2020/3/14
 **/
@Controller
public class archivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        List<Blog> blogs = blogService.listBlog(new Blog());
        Integer blogNum = blogs.size();
        Map<String,List<Blog>> map = new HashMap<String, List<Blog>>();
        List<String> years = blogService.selectYears();
        for(String year:years){
            map.put(year,blogService.selectByYear(year));
        }

        model.addAttribute("blogNum",blogNum);
        model.addAttribute("archiveMap",map);
        return "archives";
    }
}
