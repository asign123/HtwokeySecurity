package com.htwokey.htwokeysecurity.dao;

import com.htwokey.htwokeysecurity.entity.AdminMenu;
import com.htwokey.htwokeysecurity.entity.AdminResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchbo
 * @date 2023/3/29 16:35
 */
public interface AdminRoleDao {

    /**
     * 根据后台用户ID获取菜单
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<AdminMenu> getMenuList(@Param("userId") Long userId);
    /**
     * 根据角色ID获取菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<AdminMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据角色ID获取资源
     * @param roleId 角色ID
     * @return  资源列表
     */
    List<AdminResource> getResourceListByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量删除角色资源关系
     * @param ids 角色ID列表
     */
    void deleteRoleMenuRelation(@Param("ids") List<Long> ids);

    /**
     * 批量删除角色资源关系
     * @param ids 角色ID列表
     */
    void deleteRoleResourceRelation(@Param("ids") List<Long> ids);

    /**
     * 批量删除角色权限关系
     * @param ids 角色ID列表
     */
    void deleteRolePermissionRelation(List<Long> ids);

    /**
     * 批量删除角色用户关系
     * @param ids 角色ID列表
     */
    void deleteUserRoleRelation(List<Long> ids);
}
