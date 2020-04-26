package com.qiuzhao.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.domain.Tag;
import com.qiuzhao.blog.domain.Type;
import com.qiuzhao.blog.service.BlogService;
import com.qiuzhao.blog.service.TagService;
import com.qiuzhao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/14
 **/
@Controller
public class AboutController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/about")
    public String about(Model model){
        // recommend分页
        PageHelper.startPage(1,4,"t_blog.update_time desc");
        List<Blog> recommendBlogs = blogService.listBlogAboutRecommend();
        PageInfo<Blog> recommendPageInfo = new PageInfo<Blog>(recommendBlogs);

        List<Type> types = typeService.listType();
        List<Tag> tags = tagService.listTag();
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("recommendPage",recommendBlogs);
        return "about";
    }
}
