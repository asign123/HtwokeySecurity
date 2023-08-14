package com.htwokey.htwokeysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author hch
 * @since 2023-05-10
 */
@Getter
@Setter
@TableName("htwokey_security.t_admin_operation_log")
public class AdminOperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作用户
     */
    private Integer userId;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 调用地址
     */
    private String url;

    /**
     * 调用方法
     */
    private String method;

    /**
     * 调用参数
     */
    private String params;

    /**
     * 用户UA标识
     */
    private String userAgent;

    /**
     * 操作时间
     */
    private Date createTime;
}
