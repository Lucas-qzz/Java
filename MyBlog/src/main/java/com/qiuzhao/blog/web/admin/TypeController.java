package com.qiuzhao.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiuzhao.blog.domain.Type;
import com.qiuzhao.blog.service.TypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/8
 **/
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(required = false, value = "pageNum") Integer pageNum, Model model){
        if(pageNum==null || pageNum==0){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,5,"name");
        List<Type> types = typeService.listType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(types);
        model.addAttribute("page",pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/input")
    public String jumpUpdateType(@PathVariable Integer id,Model model){
        // 点击编辑，获取当前类别的值，并返回到type-input页面
        model.addAttribute("type",typeService.getType(id));
        return "admin/type-input";
    }

    @PostMapping("/types")
    public String saveType(@Valid Type type, BindingResult result ,RedirectAttributes attributes){
        // @Valid Type type, BindingResult result必须要挨在一起
        if(typeService.getTypeByName(type.getName()) != null){
            result.rejectValue("name","nameError","该类别已存在，请重新添加！");
        }
        if(result.hasErrors()){
            // 后台校验
            return "admin/type-input";
        }
        Integer id = typeService.saveType(type);
        if(id == null){
            attributes.addFlashAttribute("message","添加失败！");
        }else {
            attributes.addFlashAttribute("message","添加成功！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String updateType(@Valid Type type, BindingResult result ,
                             @PathVariable Integer id,
                             RedirectAttributes attributes){
        if(typeService.getTypeByName(type.getName()) != null){
            result.rejectValue("name","nameError","该类别已存在，请重新添加！");
        }
        if(result.hasErrors()){
            // 后台校验
            return "admin/type-input";
        }
        System.out.println(id);
        System.out.println(type);
        boolean flag = typeService.updateType(id,type);
        if(!flag){
            attributes.addFlashAttribute("message","修改失败！");
        }else {
            attributes.addFlashAttribute("message","修改成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Integer id,RedirectAttributes attributes){
        if(typeService.deleteType(id)){
            attributes.addFlashAttribute("message","删除成功！");
        }else {
            attributes.addFlashAttribute("message","删除失败！");
        }
        return "redirect:/admin/types";
    }
}
