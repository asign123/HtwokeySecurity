package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminMenu;
import com.htwokey.htwokeysecurity.entity.AdminPermission;
import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminRole;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hchbo
 * @since 2023/4/18 9:30
 */
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Autowired
    private AdminRoleService roleService;

    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<?>> list(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize,
                                            @RequestParam(value = "keyword", required = false) String keyword) {
        List<AdminRole> list = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @PutMapping("/status")
    @ResponseBody
    public CommonResult<?> updateStatus(@RequestParam("id") Long id,
                                        @RequestParam("status") Integer status) {
        AdminRole role = new AdminRole();
        role.setId(id);
        role.setStatus(status);
        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping()
    @ResponseBody
    public CommonResult<?> create(@RequestBody AdminRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 修改角色
     */
    @PutMapping()
    @ResponseBody
    public CommonResult<?> update(@RequestBody AdminRole role) {
        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public CommonResult<?> delete(@PathVariable Long id) {
        List<Long> ids = List.of(id);
        int count = roleService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 批量删除角色
     */
    @DeleteMapping("/batch")
    @ResponseBody
    public CommonResult<?> deleteBatch(@RequestParam("id") List<Long> ids) {
        int count = roleService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取角色菜单
     */
    @GetMapping("/menu/{roleId}")
    @ResponseBody
    public CommonResult<?> getMenuList(@PathVariable Long roleId) {
        List<AdminMenu> menuList = roleService.listMenu(roleId);
        return CommonResult.success(menuList);
    }

    /**
     * 给角色分配菜单
     */
    @PutMapping("/menu")
    @ResponseBody
    public CommonResult<?> allocMenu(@RequestParam("roleId") Long roleId,
                                     @RequestParam("menuIds") List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取角色资源
     */
    @GetMapping("/resource/{roleId}")
    @ResponseBody
    public CommonResult<?> getResourceList(@PathVariable Long roleId) {
        List<AdminResource> resourceList = roleService.listResource(roleId);
        return CommonResult.success(resourceList);
    }

    /**
     * 给角色分配资源
     */
    @PutMapping("/resource")
    @ResponseBody
    public CommonResult<?> allocResource(@RequestParam("roleId") Long roleId,
                                         @RequestParam("resourceIds") List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取角色权限
     */
    @GetMapping("/permission/{roleId}")
    @ResponseBody
    public CommonResult<?> getPermissionList(@PathVariable Long roleId) {
        List<AdminPermission> permissionList = roleService.listPermission(roleId);
        return CommonResult.success(permissionList);
    }

    /**
     * 给角色分配权限
     */
    @PutMapping("/permission")
    @ResponseBody
    public CommonResult<?> allocPermission(@RequestParam("roleId") Long roleId,
                                           @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = roleService.allocPermission(roleId, permissionIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
