package com.htwokey.htwokeysecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.entity.AdminUserLoginLog;
import com.htwokey.htwokeysecurity.mapper.AdminUserLoginLogMapper;
import com.htwokey.htwokeysecurity.service.AdminUserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统登录日志管理
 * @author hchbo
 * @date 2023/4/21 10:18
 */
@Service
public class AdminUserLoginLogServiceImpl implements AdminUserLoginLogService {

    @Autowired
    private AdminUserLoginLogMapper logMapper;

    @Override
    public List<AdminUserLoginLog> list(Integer pageNum, Integer pageSize, String startTime, String endTime) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminUserLoginLog> wrapper = new QueryWrapper<>();
        if (startTime != null && endTime != null){
            wrapper.between("create_time", startTime, endTime);
        }
        return logMapper.selectList(wrapper);
    }
}
