package com.htwokey.htwokeysecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.entity.AdminDictData;
import com.htwokey.htwokeysecurity.entity.AdminDictType;
import com.htwokey.htwokeysecurity.mapper.AdminDictDataMapper;
import com.htwokey.htwokeysecurity.mapper.AdminDictTypeMapper;
import com.htwokey.htwokeysecurity.service.AdminDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hchbo
 * @date 2023/6/19 13:49
 */
@Service
public class AdminDictServiceImpl implements AdminDictService {
    @Autowired
    private AdminDictTypeMapper dictTypeMapper;
    @Autowired
    private AdminDictDataMapper dictDataMapper;

    @Override
    public List<AdminDictType> selectDictTypeList(AdminDictType dict, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminDictType> wrapper = new QueryWrapper<>();
        if (dict.getName() != null) {
            wrapper.like("name", dict.getName());
        }
        if (dict.getStatus() != null) {
            wrapper.eq("status", dict.getStatus());
        }
        return dictTypeMapper.selectList(wrapper);
    }

    @Override
    public int addDictType(AdminDictType dict) {
        dict.setCreateTime(new Date());
        return dictTypeMapper.insert(dict);
    }

    @Override
    public int updateDictType(AdminDictType dict) {
        dict.setUpdateTime(new Date());
        return dictTypeMapper.updateById(dict);
    }

    @Override
    public int deleteDictTypeByIds(Long[] ids) {
        return dictTypeMapper.deleteBatchIds(List.of(ids));
    }

    @Override
    public List<AdminDictData> selectDictDataList(AdminDictData data, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminDictData> wrapper = new QueryWrapper<>();
        if (data.getLable() != null) {
            wrapper.like("lable", data.getLable());
        }
        if (data.getStatus() != null) {
            wrapper.eq("status", data.getStatus());
        }
        return dictDataMapper.selectList(wrapper);
    }

    @Override
    public int insertDictData(AdminDictData dict) {
        dict.setCreateTime(new Date());
        return dictDataMapper.insert(dict);
    }

    @Override
    public int updateDictData(AdminDictData dict) {
        dict.setUpdateTime(new Date());
        return dictDataMapper.updateById(dict);
    }

    @Override
    public int deleteDictDataByIds(Long[] ids) {
        return dictDataMapper.deleteBatchIds(List.of(ids));
    }

    @Override
    public List<AdminDictData> selectDictDataByType(String dictName) {
        QueryWrapper<AdminDictType> wrapper = new QueryWrapper<>();
        wrapper.eq("name", dictName);
        AdminDictType dictType = dictTypeMapper.selectOne(wrapper);
        if (dictType != null) {
            QueryWrapper<AdminDictData> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("dict_Type_id", dictType.getId());
            return dictDataMapper.selectList(wrapper2);
        }
        return null;
    }
}
