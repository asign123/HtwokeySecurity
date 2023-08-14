package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminMenu;
import com.htwokey.htwokeysecurity.entity.AdminUserLoginLog;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.service.AdminUserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统登录日志管理
 * @author hchbo
 * @date 2023/4/21 10:14
 */
@RestController
@RequestMapping("/admin/login/log")
public class AdminUserLoginLogController {

    @Autowired
    private AdminUserLoginLogService logService;

    /**
     * 获取系统登录日志列表
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<?>> list(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "startTime",required = false) String startTime,
                                         @RequestParam(value = "endTime",required = false) String endTime) {
        List<AdminUserLoginLog> list = logService.list(pageNum, pageSize, startTime, endTime);
        return CommonResult.success(CommonPage.restPage(list));
    }
}
