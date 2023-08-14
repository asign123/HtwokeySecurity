package com.htwokey.htwokeysecurity.entity.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 菜单查询参数
 * @author hchbo
 * @date 2023/4/21 11:09
 */
@Getter
@Setter
public class AdminMenuParam {
    private String title;
    private Integer type;
    private String startTime;
    private String endTime;
}
