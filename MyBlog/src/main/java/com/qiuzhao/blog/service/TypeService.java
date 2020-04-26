package com.qiuzhao.blog.service;

import com.qiuzhao.blog.domain.Type;


import java.util.List;

public interface TypeService {
    /**
     * @date 2020/3/8 11:38
     * @author 小朝
     * @Description //增加分类
     * @param type 增加的type
     * @return java.lang.Integer
     **/
    Integer saveType(Type type);

    /**
     * @date 2020/3/8 11:38
     * @author 小朝
     * @Description //查看分类
     * @param id type的id
     * @return com.qiuzhao.blog.domain.Type
     **/
    Type getType(Integer id);

    /**
     * @date 2020/3/8 16:45
     * @author 小朝
     * @Description //根据名称来查找
     * @param  name // 输入的名称
     * @return com.qiuzhao.blog.domain.Type
     **/
    Type getTypeByName(String name);

    /**
     * @date 2020/3/8 11:39
     * @author 小朝
     * @Description //返回type列表
     * @return org.springframework.data.domain.Page<com.qiuzhao.blog.domain.Type>
     **/
    List<Type> listType();

    /**
     * @date 2020/3/8 11:39
     * @author 小朝
     * @Description //修改标签
     * @param id 根据id找到type
     * @param type 用传入的type进行修改
     * @return 判断是否修改成功
     **/
    boolean updateType(Integer id, Type type);

    /**
     * @date 2020/3/8 17:30
     * @author 小朝
     * @Description // 根据id删除type
     * @param id
     * @return boolean 判断是否修改成功
     **/
    boolean deleteType(Integer id);
}
