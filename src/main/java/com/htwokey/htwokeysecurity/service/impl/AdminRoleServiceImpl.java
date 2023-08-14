package com.htwokey.htwokeysecurity.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.dao.AdminRoleDao;
import com.htwokey.htwokeysecurity.entity.*;
import com.htwokey.htwokeysecurity.mapper.AdminRoleMapper;
import com.htwokey.htwokeysecurity.mapper.AdminRoleMenuRelationMapper;
import com.htwokey.htwokeysecurity.mapper.AdminRoleResourceRelationMapper;
import com.htwokey.htwokeysecurity.service.AdminCacheService;
import com.htwokey.htwokeysecurity.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 后台角色管理Service实现类
 * @author hchbo
 * @date 2023/4/14 14:13
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private AdminRoleMapper roleMapper;
    @Autowired
    private AdminRoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private AdminRoleResourceRelationMapper roleResourceRelationMapper;
    @Autowired
    private AdminRoleDao roleDao;
    @Autowired
    private AdminCacheService adminCacheService;
    @Override
    public int create(AdminRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setStatus(1);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int update(AdminRole role) {
        return roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<Long> ids) {
        // 先删除角色和用户关系
        roleDao.deleteUserRoleRelation(ids);
        // 再删除角色和菜单关系
        roleDao.deleteRoleMenuRelation(ids);
        // 再删除角色和权限关系
        roleDao.deleteRolePermissionRelation(ids);
        // 再删除角色和资源关系
        roleDao.deleteRoleResourceRelation(ids);
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        // 最后删除角色
        int count = roleMapper.delete(wrapper);
        // 删除缓存
        adminCacheService.delResourceListByRoleIds(ids);
        return count;
    }

    @Override
    public List<AdminRole> list() {
        return roleMapper.selectList(null);
    }

    @Override
    public List<AdminRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(keyword)) {
            wrapper.like("name", keyword);
        }
        return roleMapper.selectList(wrapper);
    }

    @Override
    public List<AdminMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<AdminResource> listResource(Long roleId) {
        return roleDao.getResourceListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        QueryWrapper<AdminRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        roleMenuRelationMapper.delete(wrapper);
        //批量插入新关系
        for (Long menuId : menuIds) {
            AdminRoleMenuRelation relation = new AdminRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        QueryWrapper<AdminRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        roleResourceRelationMapper.delete(wrapper);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            AdminRoleResourceRelation relation = new AdminRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }

    @Override
    public List<AdminPermission> listPermission(Long roleId) {
        return null;
    }

    @Override
    public int allocPermission(Long roleId, List<Long> permissionIds) {
        return 0;
    }
}
