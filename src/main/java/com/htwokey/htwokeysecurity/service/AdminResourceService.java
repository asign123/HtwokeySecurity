package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminResourceCategory;

import java.util.List;

/**
 * 资源管理
 * @author hchbo
 * @date 2023/4/20 14:34
 */
public interface AdminResourceService {

    /**
     * 获取所有资源列表
     * @param pageSize 分页大小
     * @param pageNum 分页页码
     * @return List<AdminResource>
     */
    List<AdminResource> listAllResource(Integer pageSize, Integer pageNum);

    /**
     * 获取资源列表
     * @param pageSize 分页大小
     * @param pageNum 分页页码
     * @param resource 查询条件
     * @return List<AdminResource>
     */
    List<AdminResource> listResource(Integer pageSize, Integer pageNum,AdminResource resource);

    /**
     * 根据ID获取资源
     * @param id 资源ID
     * @return AdminResource
     */
    AdminResource getResource(Long id);

    /**
     * 添加资源
     * @param resource 资源
     * @return int
     */
    int createResource(AdminResource resource);

    /**
     * 修改资源
     * @param resource 资源
     * @return int
     */
    int updateResource(AdminResource resource);

    /**
     * 删除资源
     * @param id 资源ID
     * @return int
     */
    int deleteResource(Long id);

    /**
     * 批量删除资源
     * @param ids 资源ID
     * @return int
     */
    int deleteResource(List<Long> ids);

    /**
     * 获取所有资源分类
     * @param pageNum 分页页码
     * @param pageSize 分页大小
     * @param name 分类名称
     * @return List<AdminResourceCategory>
     */
    List<AdminResourceCategory> listResourceCategory(Integer pageNum, Integer pageSize, String name);

    /**
     * 添加资源分类
     * @param category 资源分类
     * @return int
     */
    int createResourceCategory(AdminResourceCategory category);

    /**
     * 修改资源分类
     * @param category 资源分类
     * @return int
     */
    int updateResourceCategory(AdminResourceCategory category);

    /**
     * 删除资源分类
     * @param id 资源分类ID
     * @return int
     */
    int deleteResourceCategory(Long id);

    /**
     * 所有资源分类
     * @return List<AdminResourceCategory>
     */
    List<AdminResourceCategory> listAllResourceCategory();
}
