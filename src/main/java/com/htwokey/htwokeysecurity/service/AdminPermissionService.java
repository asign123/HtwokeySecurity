package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminPermission;

import java.util.List;

/**
 * 权限管理
 * @author hchbo
 * @since 2023/4/20 15:10
 */
public interface AdminPermissionService {

    /**
     * 获取所有权限列表
     * @return List<AdminPermission>
     */
    List<AdminPermission> listAllPermission();

    /**
     * 获取权限列表
     * @param permission 查询条件
     * @param pageSize 分页大小
     * @param pageNum 分页页码
     * @return List<AdminPermission>
     */
    List<AdminPermission> listPermission(AdminPermission permission, Integer pageSize, Integer pageNum);

    /**
     * 根据ID获取权限
     * @param id 权限ID
     * @return AdminPermission
     */
    AdminPermission getPermission(Long id);

    /**
     * 添加权限
     * @param permission 权限
     * @return int
     */
    int createPermission(AdminPermission permission);

    /**
     * 修改权限
     * @param permission 权限
     * @return int
     */
    int updatePermission(AdminPermission permission);

    /**
     * 删除权限
     * @param id 权限ID
     * @return int
     */
    int deletePermission(Long id);

    /**
     * 批量删除权限
     * @param ids 权限ID列表
     * @return int
     */
    int deletePermission(List<Long> ids);

}
