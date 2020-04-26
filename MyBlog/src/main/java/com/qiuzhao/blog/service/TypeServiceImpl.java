package com.qiuzhao.blog.service;

import com.qiuzhao.blog.dao.TypeDao;
import com.qiuzhao.blog.domain.Type;
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
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;

    @Transactional
    @Override
    public Integer saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Integer id) {
        return typeDao.getType(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Type> listType(){
        return typeDao.listType();
    };

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public boolean updateType(Integer id, Type type) {
        return typeDao.updateType(id,type);
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public boolean deleteType(Integer id) {
        return typeDao.deleteType(id);
    }
}
