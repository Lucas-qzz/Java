package com.qiuzhao.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Tag;
import com.qiuzhao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/8
 **/
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@RequestParam(required = false, value = "pageNum") Integer pageNum, Model model){
        if(pageNum==null || pageNum==0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5,"id");
        List<Tag> tags = tagService.listTag();
        PageInfo<Tag> pageInfo = new PageInfo<Tag>(tags);
        model.addAttribute("page",pageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }

    @GetMapping("/tags/{id}/input")
    public String jumpUpdateTag(@PathVariable Integer id,Model model){
        // 点击编辑，获取当前类别的值，并返回到tag-input页面
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tag-input";
    }

    @PostMapping("/tags")
    public String saveTag(@Valid Tag tag, BindingResult result ,RedirectAttributes attributes){
        // @Valid Tag tag, BindingResult result必须要挨在一起
        if(tagService.getTagByName(tag.getName()) != null){
            result.rejectValue("name","nameError","该标签已存在，请重新添加！");
        }
        if(result.hasErrors()){
            // 后台校验
            return "admin/tag-input";
        }
        Integer id = tagService.saveTag(tag);
        if(id == null){
            attributes.addFlashAttribute("message","添加失败！");
        }else {
            attributes.addFlashAttribute("message","添加成功！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String updateTag(@Valid Tag tag, BindingResult result ,
                             @PathVariable Integer id,
                             RedirectAttributes attributes){
        if(tagService.getTagByName(tag.getName()) != null){
            result.rejectValue("name","nameError","该标签已存在，请重新添加！");
        }
        if(result.hasErrors()){
            // 后台校验
            return "admin/tag-input";
        }
        boolean flag = tagService.updateTag(id,tag);
        if(!flag){
            attributes.addFlashAttribute("message","修改失败！");
        }else {
            attributes.addFlashAttribute("message","修改成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Integer id,RedirectAttributes attributes){
        if(tagService.deleteTag(id)){
            attributes.addFlashAttribute("message","删除成功！");
        }else {
            attributes.addFlashAttribute("message","删除失败！");
        }
        return "redirect:/admin/tags";
    }
}
