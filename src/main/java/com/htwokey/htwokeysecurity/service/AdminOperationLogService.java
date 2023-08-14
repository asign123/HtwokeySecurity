package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminOperationLog;
import com.htwokey.htwokeysecurity.entity.dto.AdminOperationParam;

import java.util.List;

/**
 * @author hchbo
 * @since 2023/5/10 10:01
 * @description 操作日志
 */
public interface AdminOperationLogService {
    /**
     * 保存操作日志
     *
     * @param operationLog 操作日志
     */
    void save(AdminOperationLog operationLog);

    /**
     * 获取操作日志列表
     * @param param 查询参数
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 操作日志列表
     */
    List<AdminOperationLog> list(AdminOperationParam param, Integer pageNum, Integer pageSize);

    /**
     * 清空操作日志
     */
    void clear();
}
