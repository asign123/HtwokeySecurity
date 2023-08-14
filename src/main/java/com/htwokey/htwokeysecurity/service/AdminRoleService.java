package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminMenu;
import com.htwokey.htwokeysecurity.entity.AdminPermission;
import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminRole;

import java.util.List;

/**
 * @author hchbo
 * @date 2023/4/14 14:03
 */
public interface AdminRoleService {
    /**
     * 添加角色
     * @param role 角色信息
     * @return 添加结果
     */
    int create(AdminRole role);

    /**
     * 修改角色信息
     * @param role 角色信息
     * @return 修改结果
     */
    int update(AdminRole role);

    /**
     * 批量删除角色
     * @param ids 角色ID列表
     * @return 删除结果
     */
    int delete(List<Long> ids);

    /**
     * 获取所有角色列表
     * @return 角色列表
     */
    List<AdminRole> list();

    /**
     * 分页获取角色列表
     * @param keyword 关键字
     * @param pageSize 分页大小
     * @param pageNum 分页页码
     * @return 角色列表
     */
    List<AdminRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 获取角色相关菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<AdminMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<AdminResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 分配结果
     */
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     * @param roleId 角色ID
     * @param resourceIds 资源ID列表
     * @return 分配结果
     */
    int allocResource(Long roleId, List<Long> resourceIds);

    /**
     * 获取角色相关权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<AdminPermission> listPermission(Long roleId);

    /**
     * 给角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @return 分配结果
     */
    int allocPermission(Long roleId, List<Long> permissionIds);
}
