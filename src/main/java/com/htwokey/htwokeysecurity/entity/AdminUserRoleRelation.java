package com.htwokey.htwokeysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 后台用户和角色关系表
 * </p>
 *
 * @author hch
 * @since 2023-04-13
 */
@Getter
@Setter
@TableName("htwokey_security.t_admin_user_role_relation")
public class AdminUserRoleRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long roleId;
}
