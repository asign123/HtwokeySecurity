package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminMenu;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.entity.dto.AdminMenuParam;
import com.htwokey.htwokeysecurity.entity.vo.TreeSelect;
import com.htwokey.htwokeysecurity.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 * @author hchbo
 * @date 2023/3/30 10:27
 */
@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    private AdminMenuService menuService;

    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<List<AdminMenu>> list(AdminMenuParam menu) {
        List<AdminMenu> menus = menuService.selectMenuList(menu);
        return CommonResult.success(menus);
    }

    /**
     * 根据菜单id获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    @ResponseBody
    public CommonResult<AdminMenu> getMenuInfo(@PathVariable Long menuId) {
        return CommonResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 新增菜单
     */
    @PostMapping()
    @ResponseBody
    public CommonResult<?> create(@RequestBody AdminMenu menu) {
        int count = menuService.insertMenu(menu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 修改菜单
     */
    @PutMapping()
    @ResponseBody
    public CommonResult<?> update(@RequestBody AdminMenu menu) {
        int count = menuService.updateMenu(menu);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    @ResponseBody
    public CommonResult<?> delete(@PathVariable Long menuId) {
        int count = menuService.deleteMenuById(menuId);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree")
    @ResponseBody
    public CommonResult<List<TreeSelect>> treeSelect() {
        List<AdminMenu> menus = menuService.selectAllMenuList();
        List<TreeSelect> treeSelects = menuService.buildMenuTreeSelect(menus);
        return CommonResult.success(treeSelects);
    }
}
