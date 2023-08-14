package com.htwokey.htwokeysecurity.dao;

import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminRole;
import com.htwokey.htwokeysecurity.entity.AdminUserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchbo
 * @date 2023/3/29 14:04
 */

public interface AdminUserRoleRelationDao {
    /**
     * 批量插入用户角色关系
     * @param adminUserRoleRelations 用户角色关系列表
     * @return 插入数量
     */
    int insertList(@Param("list") List<AdminUserRoleRelation> adminUserRoleRelations);

    /**
     * 获取用于所有角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<AdminRole> getRoleList(@Param("userId") Long userId);

    /**
     * 获取用户所有可访问资源
     * @param userId 用户id
     * @return 资源列表
     */
    List<AdminResource> getResourceList(@Param("userId") Long userId);

    /**
     * 获取资源相关用户ID列表
     * @param resourceId 资源id
     * @return 用户id列表
     */
    List<Long> getAdminUserIdList(@Param("resourceId") Long resourceId);
}
