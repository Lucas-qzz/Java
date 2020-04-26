package com.qiuzhao.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.qiuzhao.blog.dao.BlogDao;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.domain.Tag;
import com.qiuzhao.blog.domain.Type;
import com.qiuzhao.blog.exception.NotFoundException;
import com.qiuzhao.blog.service.BlogService;
import com.qiuzhao.blog.service.BlogTagService;
import com.qiuzhao.blog.service.TagService;
import com.qiuzhao.blog.service.TypeService;
import com.qiuzhao.blog.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author: 小朝
 * @date: 2020/3/12
 **/
@Controller
public class indexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private BlogDao blogDao;

    @GetMapping("/")
    public String index(@RequestParam(required = false, value = "blogPageNum") Integer blogPageNum,
                        Model model){
        if(blogPageNum==null || blogPageNum==0){
            blogPageNum = 1;
        }
        // 博客分页
        PageHelper.startPage(blogPageNum,5,"t_blog.update_time desc");
        List<Blog> blogs = blogService.listBlog(new Blog());
        PageInfo<Blog> blogPageInfo= new PageInfo<Blog>(blogs);

//        // type分页
//        PageHelper.startPage(typePageNum,5,"t_type.name");
        List<Type> types = typeService.listType();
        for(Type type : types){
            type.setBlogs(blogService.countByTypes(type.getId()));
        }
//        PageInfo<Type> typePageInfo = new PageInfo<Type>(types);

        // tag分页
//        PageHelper.startPage(tagPageNum,5,"t_tag.name");
        List<Tag> tags = tagService.listTag();
        for(Tag tag:tags){
            List<Integer> blogIds = blogTagService.getPartOfListBlog(tag.getId());
            List<Blog> tag_blogs = new ArrayList<Blog>();
            for(Integer blog_id: blogIds){
                Blog blog = blogService.getBlog(blog_id);
                tag_blogs.add(blog);
            }
            tag.setBlogs(tag_blogs);
        }
//        PageInfo<Tag> tagPageInfo = new PageInfo<Tag>(tags);
        // recommend分页
        PageHelper.startPage(1,4,"t_blog.update_time desc");
        List<Blog> recommendBlogs = blogService.listBlogAboutRecommend();
        PageInfo<Blog> recommendPageInfo = new PageInfo<Blog>(recommendBlogs);

        model.addAttribute("recommendPage",recommendBlogs);
        model.addAttribute("typePage",types);
        model.addAttribute("tagPage",tags);
        model.addAttribute("blogPage",blogPageInfo);
        return "index";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(required = false, value = "blogPageNum") Integer blogPageNum,
                         @RequestParam(required = false) String query,
                         HttpServletRequest request,
                         Model model){
        if(blogPageNum==null || blogPageNum==0){
            blogPageNum = 1;
        }
        if("".equals(query) || query==null){
            query = request.getParameter("query");
        }
//        PageHelper.startPage(blogPageNum,3,"t_blog.update_time desc");
        List<Blog> blogs = blogService.searchBlogs("%"+query+"%");
        PageInfo<Blog> blogPageInfo= new PageInfo<Blog>(blogs);
        model.addAttribute("query",query);
        model.addAttribute("blogPage",blogPageInfo);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id, Model model,HttpServletRequest request,
                        HttpServletResponse response){
        boolean flag = true;
        String ip = request.getLocalAddr();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(ip+id)){
                    cookie.setMaxAge(3600);
                    flag = false;
                }
            }
        }
        if(flag){
            blogService.updateBlogViews(id);
            Cookie newCookie = new Cookie(ip+id,ip);
            newCookie.setMaxAge(3600);
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }

        Blog blog = blogService.getBlog(id);
        if(blog == null){
            throw new NotFoundException("该blog不存在！");
        }
        Blog newBlog = new Blog();
        BeanUtils.copyProperties(blog,newBlog);
        // 将blog中的content转HTML,不改变数据库的值
        newBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(newBlog.getContent()));
        // 获取该blog的tags
        List<Integer> ids = blogTagService.getPartOfListTag(id);
        List<Tag> tags = new ArrayList<Tag>();
        for(Integer tagid :ids){
            tags.add(tagService.getTag(tagid));
        }
        newBlog.setTags(tags);
        model.addAttribute("blog",newBlog);
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String footer(Model model){
        List<Blog> newBlogs = blogService.selectNewBlog();
        model.addAttribute("newBlogs",newBlogs);
        return "_fragements::newBlogList";
    }



    @ResponseBody
    @GetMapping("/test")
    public String test(HttpServletRequest request){
        Blog blog = blogDao.test(1,"第一篇博文");
        Gson gson = new Gson();
        gson.toJson(blog);
        return gson.toJson(blog);
    }
}
