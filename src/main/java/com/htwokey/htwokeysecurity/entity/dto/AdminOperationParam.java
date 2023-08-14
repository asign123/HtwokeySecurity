package com.htwokey.htwokeysecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hchbo
 * @date 2023/5/11 11:53
 * @description 操作日志查询参数
 */
@Getter
@Setter
public class AdminOperationParam {

    /**
     * 操作用户
     */
    private String username;
    /**
     * 调用地址
     */
    private String url;

    /**
     * 调用方法
     */
    private String method;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
