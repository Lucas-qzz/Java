package com.qiuzhao.blog.service;

import com.qiuzhao.blog.dao.TagDao;
import com.qiuzhao.blog.domain.Tag;
import com.qiuzhao.blog.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 小朝
 * @date: 2020/3/8
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    @Transactional
    @Override
    public Integer saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Integer id) {
        return tagDao.getTag(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Tag> listTag(){
        return tagDao.listTag();
    };

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public boolean updateTag(Integer id, Tag tag) {
        return tagDao.updateTag(id,tag);
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public boolean deleteTag(Integer id) {
        return tagDao.deleteTag(id);
    }

    @Override
    public List<Tag> listTagOfPart(String[] ids) {
        return tagDao.listTagOfPart(ids);
    }
}
