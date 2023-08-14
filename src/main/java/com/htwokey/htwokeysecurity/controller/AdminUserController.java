package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminRole;
import com.htwokey.htwokeysecurity.entity.AdminUser;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.entity.dto.AdminUserParam;
import com.htwokey.htwokeysecurity.service.AdminUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @author hchbo
 * @date 2023/3/29 10:23
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService userService;

    public AdminUserController(AdminUserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/register")
    @ResponseBody
    public CommonResult<AdminUser> register(@Validated @RequestBody AdminUserParam userParam) {
        AdminUser umsAdmin = userService.register(userParam);
        if (umsAdmin == null) {
            return CommonResult.failed("用户名已存在");
        }
        return CommonResult.success(umsAdmin);
    }

    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<AdminUser>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AdminUser> userList = userService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(userList));
    }


    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<AdminUser> getItem(@PathVariable Long id) {
        AdminUser user = userService.getItem(id);
        return CommonResult.success(user);
    }


    @PutMapping("/update")
    @ResponseBody
    public CommonResult<?> update(@RequestBody AdminUser user) {
        if (user.getId() == null) {
            return CommonResult.failed("id不能为空");
        }
        int count = userService.update(user);
        if (count > 0) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public CommonResult<?> delete(@PathVariable Long id) {
        int count = userService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除失败");
    }

    @PutMapping("/updateStatus")
    @ResponseBody
    public CommonResult<?> updateStatus(@RequestParam(value = "id") Long id,
                                        @RequestParam(value = "status") Integer status) {
        if (id == 1){
            return CommonResult.failed("超级管理员不允许修改");
        }
        AdminUser user = new AdminUser();
        user.setStatus(status);
        user.setId(id);
        int count = userService.update(user);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PutMapping("/role/update")
    @ResponseBody
    public CommonResult<?> updateRole(@RequestParam("userId") Long userId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.updateRole(userId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @GetMapping("/role/{userId}")
    @ResponseBody
    public CommonResult<List<AdminRole>> getRoleList(@PathVariable Long userId) {
        List<AdminRole> roleList = userService.getRoleList(userId);
        return CommonResult.success(roleList);
    }
}
