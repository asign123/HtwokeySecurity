package com.htwokey.htwokeysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author hch
 * @since 2023-04-13
 */
@Getter
@Setter
@TableName("htwokey_security.t_admin_menu")
public class AdminMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 普通页面归属菜单
     */
    private String activeMenu;

    /**
     * 菜单类型：1.顶级菜单，2，二级菜单，9普通页面
     */
    private String type;

    /**
     * 外链地址
     */
    private String isLink;

    /**
     * 是否隐藏
     */
    private Integer isHide;

    /**
     * 是否全屏
     */
    private Integer isFull;

    /**
     * 是否固定再tabs nav
     */
    private Integer isAffix;

    /**
     * 是否缓存
     */
    private Integer isKeepAlive;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /** 子菜单 */
    @TableField(exist = false)
    private List<AdminMenu> children = new ArrayList<>();

}
