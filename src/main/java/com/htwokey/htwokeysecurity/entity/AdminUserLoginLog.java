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
 * 后台用户登录日志表
 * </p>
 *
 * @author hch
 * @since 2023-04-13
 */
@Getter
@Setter
@TableName("htwokey_security.t_admin_user_login_log")
public class AdminUserLoginLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Date createTime;
    private String ip;
    private String address;

    /**
     * 浏览器登录类型
     */
    private String userAgent;
}
