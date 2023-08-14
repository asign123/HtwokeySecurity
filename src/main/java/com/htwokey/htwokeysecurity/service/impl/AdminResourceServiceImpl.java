package com.htwokey.htwokeysecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminResourceCategory;
import com.htwokey.htwokeysecurity.entity.AdminRoleResourceRelation;
import com.htwokey.htwokeysecurity.mapper.AdminResourceCategoryMapper;
import com.htwokey.htwokeysecurity.mapper.AdminResourceMapper;
import com.htwokey.htwokeysecurity.mapper.AdminRoleResourceRelationMapper;
import com.htwokey.htwokeysecurity.service.AdminResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hchbo
 * @date 2023/4/20 14:35
 */
@Service
public class AdminResourceServiceImpl implements AdminResourceService {

    @Autowired
    private AdminResourceMapper resourceMapper;

    @Autowired
    private AdminResourceCategoryMapper categoryMapper;

    @Autowired
    private AdminRoleResourceRelationMapper roleResourceRelationMapper;




    @Override
    public List<AdminResource> listAllResource(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return resourceMapper.selectList(null);
    }

    @Override
    public List<AdminResource> listResource(Integer pageSize,Integer pageNum, AdminResource resource) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminResource> queryWrapper = new QueryWrapper<>();
        if (resource.getName()!= null){
            queryWrapper.like("name", resource.getName());
        }
        if (resource.getCategoryId() != null){
            queryWrapper.eq("category_id", resource.getCategoryId());
        }
        return resourceMapper.selectList(queryWrapper);
    }

    @Override
    public AdminResource getResource(Long id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public int createResource(AdminResource resource) {
        resource.setCreateTime(new Date());
        return resourceMapper.insert(resource);
    }

    @Override
    public int updateResource(AdminResource resource) {
        if (resource.getId() == null){
            return 0;
        }
        return resourceMapper.updateById(resource);
    }

    @Override
    public int deleteResource(Long id) {
        // 删除资源时，需要删除角色资源关联表中的数据
        QueryWrapper<AdminRoleResourceRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_id", id);
        roleResourceRelationMapper.delete(queryWrapper);

        return resourceMapper.deleteById(id);
    }

    @Override
    public int deleteResource(List<Long> ids) {
        // 删除资源时，需要删除角色资源关联表中的数据
        QueryWrapper<AdminRoleResourceRelation> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("resource_id", ids);
        roleResourceRelationMapper.delete(queryWrapper1);

        QueryWrapper<AdminResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",  ids);
        return resourceMapper.delete(queryWrapper);
    }


    @Override
    public List<AdminResourceCategory> listResourceCategory(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminResourceCategory> queryWrapper = new QueryWrapper<>();
        if (name != null){
            queryWrapper.like("name", name);
        }
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public int createResourceCategory(AdminResourceCategory category) {
        category.setCreateTime(new Date());
        return categoryMapper.insert(category);
    }

    @Override
    public int updateResourceCategory(AdminResourceCategory category) {
        if (category.getId() == null){
            return 0;
        }
        return categoryMapper.updateById(category);
    }

    @Override
    public int deleteResourceCategory(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public List<AdminResourceCategory> listAllResourceCategory() {
        return categoryMapper.selectList(null);
    }
}
