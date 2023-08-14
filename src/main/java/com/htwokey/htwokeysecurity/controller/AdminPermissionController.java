package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminPermission;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.service.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理
 * @author hchbo
 * @date 2023/4/20 17:34
 */
@RestController
@RequestMapping("/admin/permission")
public class AdminPermissionController {

    @Autowired
    private AdminPermissionService permissionService;

    /**
     * 权限列表
     */
    @GetMapping("/listAll")
    @ResponseBody
    public CommonResult<?> list() {
        return CommonResult.success(permissionService.listAllPermission());
    }

    /**
     * 权限查询
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<AdminPermission>> list(AdminPermission permission,
                                                          @RequestParam("pageNum") Integer pageNum,
                                                          @RequestParam("pageSize") Integer pageSize) {
        List<AdminPermission> list = permissionService.listPermission(permission, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    /**
     * 根据id获取权限
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<AdminPermission> getItem(@PathVariable Long id) {
        AdminPermission permission = permissionService.getPermission(id);
        return CommonResult.success(permission);
    }

    /**
     * 添加权限
     */
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<?> create(@RequestBody AdminPermission permission) {
        int count = permissionService.createPermission(permission);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 修改权限
     */
    @PutMapping("/update")
    @ResponseBody
    public CommonResult<?> update(@RequestBody AdminPermission permission) {
        int count = permissionService.updatePermission(permission);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public CommonResult<?> delete(@PathVariable Long id) {
        int count = permissionService.deletePermission(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 批量删除权限
     */
    @DeleteMapping("/delete/batch")
    @ResponseBody
    public CommonResult<?> deleteBatch(@RequestParam("ids") List<Long> ids) {
        int count = permissionService.deletePermission(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
