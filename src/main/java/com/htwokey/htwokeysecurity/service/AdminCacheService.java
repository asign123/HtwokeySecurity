package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminUser;

import java.util.List;

/**
 * @author hchbo
 * @date 2023/4/14 10:18
 */
public interface AdminCacheService {
    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long adminId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     */
    AdminUser getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(AdminUser admin);

    /**
     * 获取缓存后台用户资源列表
     */
    List<AdminResource> getResourceList(Long adminId);

    /**
     * 设置缓存后台用户资源列表
     */
    void setResourceList(Long adminId, List<AdminResource> resourceList);
}
