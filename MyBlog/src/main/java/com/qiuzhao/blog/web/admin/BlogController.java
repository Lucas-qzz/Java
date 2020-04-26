package com.qiuzhao.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Blog;
import com.qiuzhao.blog.domain.Tag;
import com.qiuzhao.blog.domain.Type;
import com.qiuzhao.blog.domain.User;
import com.qiuzhao.blog.service.BlogService;
import com.qiuzhao.blog.service.BlogTagService;
import com.qiuzhao.blog.service.TagService;
import com.qiuzhao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/7
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {
    private final String INPUT ="admin/blog-input";
    private final String LIST ="admin/blogs";
    private final String REDIRECT_LIST ="redirect:/admin/blogs";

    // 全局的createTime
    private Date createTime;

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/blogs")
    public String list(@RequestParam(required = false, value = "pageNum") Integer pageNum, Model model){
        if(pageNum==null || pageNum==0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5,"t_blog.id");
        List<Blog> blogs = blogService.listBlog(new Blog());
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",pageInfo);
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@RequestParam(required = false, value = "pageNum") Integer pageNum, Model model, HttpServletRequest request){
        if(pageNum==null || pageNum==0){
            pageNum = 1;
        }
        Blog blog = new Blog();
        if("".equals(request.getParameter("title"))){
            blog.setTitle(null);
        }else {
            blog.setTitle("%"+request.getParameter("title")+"%");
        }
        String recommend = request.getParameter("recommend");
        if("on".equals(recommend)){
            blog.setRecommend(true);
        }else {
            blog.setRecommend(false);
        }
        if("".equals(request.getParameter("typeId"))){
            blog.setType(null);
        }else {
            Type type = typeService.getType(Integer.parseInt(request.getParameter("typeId")));
            blog.setType(type);
        }
        PageHelper.startPage(pageNum,5,"update_time");
        List<Blog> blogs = blogService.listBlog(blog);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs);
        model.addAttribute("page",pageInfo);
        return LIST;
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        // 保存此时的createTime
        createTime = blog.getCreateTime();
        // 把原先blog对应的tag一一查询出来
        List<Integer> ids = blogTagService.getPartOfListTag(id);
        List<Tag> tags = new ArrayList<Tag>();
        for(Integer tagid: ids){
            Tag tag = tagService.getTag(tagid);
            tags.add(tag);
        }
        blog.setTags(tags);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        if(blog.getId() == null){
            // 保存
            blog.setUpdateTime(new Date());
            blog.setCreateTime(new Date());
            blog.setViews(0);
            blog.setUser((User) session.getAttribute("user"));
            blog.setType(typeService.getType(blog.getType().getId()));
            blog.setTags(tagService.listTagOfPart(blog.getTagIds().split(",")));
            blogService.saveBlog(blog);
            Integer returnId = blog.getId();
            for(Tag tag: blog.getTags()){
                blogTagService.saveBlogAndTag(returnId,tag.getId());
            }
            if(returnId!=-1){
                attributes.addFlashAttribute("message","添加成功！");
            }else {
                attributes.addFlashAttribute("message","添加失败！");
            }
        }else {
            // 修改
            blog.setUpdateTime(new Date());
            // 将临时保存的createTime取出来;
            blog.setCreateTime(createTime);
            blog.setUser((User) session.getAttribute("user"));
            blog.setType(typeService.getType(blog.getType().getId()));
            blog.setTags(tagService.listTagOfPart(blog.getTagIds().split(",")));
            boolean flag = blogService.updateBlog(blog.getId(),blog);
            blogTagService.deleteBlogAndTag(blog.getId());
            for(Tag tag: blog.getTags()){
                blogTagService.saveBlogAndTag(blog.getId(),tag.getId());
            }
            if(flag){
                attributes.addFlashAttribute("message","修改成功！");
            }else {
                attributes.addFlashAttribute("message","修改失败！");
            }
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        boolean flag1 = blogService.deleteBlog(id);
        boolean flag2 = blogTagService.deleteBlogAndTag(id);
        if(flag1&&flag2){
            attributes.addFlashAttribute("message","删除成功！");
        }else {
            attributes.addFlashAttribute("message","删除失败！");
        }

        return REDIRECT_LIST;
    }
}
