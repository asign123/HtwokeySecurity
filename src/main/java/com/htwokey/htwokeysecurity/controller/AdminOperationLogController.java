package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminOperationLog;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.entity.dto.AdminOperationParam;
import com.htwokey.htwokeysecurity.service.AdminOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hchbo
 * @date 2023/5/11 11:31
 * @description 操作日志
 */
@RestController
@RequestMapping("/admin/operationLog")
public class AdminOperationLogController {

    @Autowired
    private AdminOperationLogService operationLogService;

    /**
     * 获取操作日志列表
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<AdminOperationLog>> list(AdminOperationParam param,
                                                            @RequestParam("pageNum") Integer pageNum,
                                                            @RequestParam("pageSize") Integer pageSize) {
        List<AdminOperationLog> list = operationLogService.list(param, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    /**
     * 清空操作日志
     */
    @DeleteMapping("/clear")
    @ResponseBody
    public CommonResult<?> clear() {
        operationLogService.clear();
        return CommonResult.success(null);
    }
}
