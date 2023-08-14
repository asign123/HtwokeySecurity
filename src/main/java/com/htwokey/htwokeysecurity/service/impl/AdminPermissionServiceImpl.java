package com.htwokey.htwokeysecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.entity.AdminPermission;
import com.htwokey.htwokeysecurity.mapper.AdminPermissionMapper;
import com.htwokey.htwokeysecurity.service.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hchbo
 * @date 2023/4/20 15:14
 */
@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {

    @Autowired
    private AdminPermissionMapper permissionMapper;

    @Override
    public List<AdminPermission> listAllPermission() {
        return permissionMapper.selectList(null);
    }

    @Override
    public List<AdminPermission> listPermission(AdminPermission permission, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminPermission> wrapper = new QueryWrapper<>();
        if (permission.getName() != null){
            wrapper.like("name", permission.getName());
        }

        return permissionMapper.selectList(wrapper);
    }

    @Override
    public AdminPermission getPermission(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public int createPermission(AdminPermission permission) {
        permission.setCreateTime(new Date());
        return permissionMapper.insert(permission);
    }

    @Override
    public int updatePermission(AdminPermission permission) {
        if (permission.getId() == null){
            return 0;
        }
        return permissionMapper.updateById(permission);
    }

    @Override
    public int deletePermission(Long id) {
        // 先删除角色权限关系
        // todo

        return permissionMapper.deleteById(id);
    }

    @Override
    public int deletePermission(List<Long> ids) {
        // 先删除角色权限关系
        // todo
        QueryWrapper<AdminPermission> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return permissionMapper.delete(wrapper);
    }
}
