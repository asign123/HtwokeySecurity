package com.htwokey.htwokeysecurity.controller;

import cn.hutool.core.collection.CollUtil;
import com.htwokey.htwokeysecurity.entity.AdminRole;
import com.htwokey.htwokeysecurity.entity.AdminUser;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.entity.dto.AdminUserLoginParam;
import com.htwokey.htwokeysecurity.entity.dto.UpdateAdminUserPasswordParam;
import com.htwokey.htwokeysecurity.entity.vo.RouterVo;
import com.htwokey.htwokeysecurity.service.AdminMenuService;
import com.htwokey.htwokeysecurity.service.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hchbo
 * @since 2023/6/19 10:46
 */

@RestController
@RequestMapping("/admin/login")
public class AdminLoginController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.header}")
    private String header;

    private final AdminUserService userService;
    private final AdminMenuService menuService;

    public AdminLoginController(AdminUserService userService, AdminMenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    @PostMapping()
    @ResponseBody
    public CommonResult<?> login(@Validated @RequestBody AdminUserLoginParam userLoginParam) {
        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @GetMapping("/info")
    @ResponseBody
    public CommonResult<?> getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        AdminUser user = userService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>(3);
        data.put("username", user.getUsername());
        data.put("menus", menuService.buildMenus(menuService.getMenusByUserId(user.getId())));
        data.put("icon", user.getIcon());
        List<AdminRole> roleList = userService.getRoleList(user.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(AdminRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }

    @GetMapping("/refreshToken")
    @ResponseBody
    public CommonResult<?> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        String refreshToken = userService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public CommonResult<?> updatePassword(@Validated @RequestBody UpdateAdminUserPasswordParam updatePasswordParam) {
        int status = userService.updatePassword(updatePasswordParam);
        return switch (status) {
            case 1 -> CommonResult.success(status);
            case -1 -> CommonResult.failed("提交参数不合法");
            case -2 -> CommonResult.failed("找不到该用户");
            case -3 -> CommonResult.failed("旧密码错误");
            default -> CommonResult.failed();
        };
    }

    @GetMapping("/menu")
    @ResponseBody
    public CommonResult<List<RouterVo>> getAdminUserMenu(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        AdminUser user = userService.getAdminByUsername(username);
        return CommonResult.success(menuService.buildMenus(menuService.getMenusByUserId(user.getId())));
    }


    @PostMapping("/logout")
    @ResponseBody
    public CommonResult<?> logout() {
        return CommonResult.success(null);
    }
}
