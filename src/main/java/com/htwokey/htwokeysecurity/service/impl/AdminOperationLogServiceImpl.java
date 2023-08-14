package com.htwokey.htwokeysecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.entity.AdminOperationLog;
import com.htwokey.htwokeysecurity.entity.dto.AdminOperationParam;
import com.htwokey.htwokeysecurity.mapper.AdminOperationLogMapper;
import com.htwokey.htwokeysecurity.service.AdminOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hchbo
 * @since  2023/5/10 10:02
 * @description 操作日志
 */
@Service
public class AdminOperationLogServiceImpl implements AdminOperationLogService {

    @Autowired
    private AdminOperationLogMapper operationLogMapper;

    @Override
    public void save(AdminOperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }

    @Override
    public List<AdminOperationLog> list(AdminOperationParam param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 拼装查询条件
        QueryWrapper<AdminOperationLog> wrapper = new QueryWrapper<>();
        if (param.getUsername()!=null) {
            wrapper.like("username", param.getUsername());
        }
        if(param.getMethod() != null){
            wrapper.eq("method", param.getMethod());
        }
        if (param.getUrl()!=null) {
            wrapper.like("url", param.getUrl());
        }
        if (param.getStartTime()!=null && param.getEndTime()!=null) {
            wrapper.between("create_time", param.getStartTime(), param.getEndTime());
        }
        wrapper.orderByDesc("create_time");
        return operationLogMapper.selectList(wrapper);
    }

    @Override
    public void clear() {
        operationLogMapper.delete(null);
    }
}
