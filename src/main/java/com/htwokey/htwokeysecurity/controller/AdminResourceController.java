package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminResourceCategory;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.service.AdminResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源管理
 * @author hchbo
 * @since 2023/4/20 14:33
 */
@RestController
@RequestMapping("/admin/resources")
public class AdminResourceController {

    @Autowired
    private AdminResourceService resourceService;

    /**
     * 获取所有资源
     */
    @GetMapping("/listAll")
    @ResponseBody
    public CommonResult<CommonPage<?>> list(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize) {
        List<AdminResource> list = resourceService.listAllResource(pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    /**
     * 资源查询
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<?>> list(AdminResource resource,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize) {
        List<AdminResource> list = resourceService.listResource(pageSize, pageNum, resource);
        return CommonResult.success(CommonPage.restPage(list));
    }

    /**
     * 根据id获取资源
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<AdminResource> getItem(@PathVariable Long id) {
        AdminResource resource = resourceService.getResource(id);
        return CommonResult.success(resource);
    }

    /**
     * 添加资源
     */
    @PostMapping()
    @ResponseBody
    public CommonResult<?> create(@RequestBody AdminResource resource) {
        int count = resourceService.createResource(resource);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 修改资源
     */
    @PutMapping()
    @ResponseBody
    public CommonResult<?> update(@RequestBody AdminResource resource) {
        int count = resourceService.updateResource(resource);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 删除资源
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public CommonResult<?> delete(@PathVariable Long id) {
        int count = resourceService.deleteResource(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 批量删除资源
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public CommonResult<?> delete(@RequestParam("id") List<Long> ids) {
        int count = resourceService.deleteResource(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 所有资源分类列表
     */
    @GetMapping("/category/all")
    @ResponseBody
    public CommonResult<List<AdminResourceCategory>> categoryListAll() {
        List<AdminResourceCategory> list = resourceService.listAllResourceCategory();
        return CommonResult.success(list);
    }

    /**
     * 资源分类列表
     */
    @GetMapping("/category/list")
    @ResponseBody
    public CommonResult<CommonPage<?>> categoryList(@RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam(value = "name",required = false) String name) {
        List<AdminResourceCategory> list = resourceService.listResourceCategory(pageNum, pageSize, name);
        return CommonResult.success(CommonPage.restPage(list));
    }

    /**
     * 添加资源分类
     */
    @PostMapping("/category")
    @ResponseBody
    public CommonResult<?> createCategory(@RequestBody AdminResourceCategory category) {
        int count = resourceService.createResourceCategory(category);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 修改资源分类
     */
    @PutMapping("/category")
    @ResponseBody
    public CommonResult<?> updateCategory(@RequestBody AdminResourceCategory category) {
        int count = resourceService.updateResourceCategory(category);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 删除资源分类
     */
    @DeleteMapping("/category/{id}")
    @ResponseBody
    public CommonResult<?> deleteCategory(@PathVariable Long id) {
        int count = resourceService.deleteResourceCategory(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
