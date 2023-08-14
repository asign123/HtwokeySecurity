package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminUserLoginLog;

import java.util.List;

/**
 * 系统登录日志管理
 * @author hchbo
 * @date 2023/4/21 10:18
 */
public interface AdminUserLoginLogService {

    /**
     * 获取系统登录日志列表
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 系统登录日志列表
     */
    List<AdminUserLoginLog> list(Integer pageNum, Integer pageSize, String startTime, String endTime);
}
