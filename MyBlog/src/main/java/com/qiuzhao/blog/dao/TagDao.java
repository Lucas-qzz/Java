package com.qiuzhao.blog.dao;

import com.qiuzhao.blog.domain.Tag;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TagDao {
    /**
     * @date 2020/3/8 11:38
     * @author 小朝
     * @Description //增加分类
     * @param Tag 增加的Tag
     * @return java.lang.Integer
     **/
    Integer saveTag(Tag Tag);

    /**
     * @date 2020/3/8 11:38
     * @author 小朝
     * @Description //查看分类
     * @param id Tag的id
     * @return com.qiuzhao.blog.domain.Tag
     **/
    Tag getTag(Integer id);

    /**
     * @date 2020/3/8 16:45
     * @author 小朝
     * @Description //根据名称来查找
     * @param  name // 输入的名称
     * @return com.qiuzhao.blog.domain.Tag
     **/
    Tag getTagByName(String name);


    /**
     * @date 2020/3/8 11:39
     * @author 小朝
     * @Description //返回Tag列表
     * @return org.springframework.data.domain.Page<com.qiuzhao.blog.domain.Tag>
     **/
    List<Tag> listTag();

    /**
     * @date 2020/3/8 11:39
     * @author 小朝
     * @Description //修改标签
     * @param id 根据id找到Tag
     * @param tag 用传入的Tag进行修改
     * @return 判断是否修改成功
     **/
    boolean updateTag(Integer id, Tag tag);

    /**
     * @date 2020/3/8 17:30
     * @author 小朝
     * @Description // 根据id删除Tag
     * @param id
     * @return boolean 判断是否修改成功
     **/
    boolean deleteTag(Integer id);

    /**
     * @date 2020/3/11 14:29
     * @author 小朝
     * @Description //获取指定id的tag
     * @param ids 多个id
     * @return java.util.List<com.qiuzhao.blog.domain.Tag>
     **/
    List<Tag> listTagOfPart(String[] ids);
}
