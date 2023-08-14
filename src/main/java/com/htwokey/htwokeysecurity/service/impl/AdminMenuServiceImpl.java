package com.htwokey.htwokeysecurity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.dao.AdminRoleDao;
import com.htwokey.htwokeysecurity.entity.AdminMenu;
import com.htwokey.htwokeysecurity.entity.AdminRoleMenuRelation;
import com.htwokey.htwokeysecurity.entity.dto.AdminMenuParam;
import com.htwokey.htwokeysecurity.entity.vo.MetaVo;
import com.htwokey.htwokeysecurity.entity.vo.RouterVo;
import com.htwokey.htwokeysecurity.entity.vo.TreeSelect;
import com.htwokey.htwokeysecurity.mapper.AdminMenuMapper;
import com.htwokey.htwokeysecurity.mapper.AdminRoleMenuRelationMapper;
import com.htwokey.htwokeysecurity.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hchbo
 * @date 2023/4/15 18:42
 */
@Service
public class AdminMenuServiceImpl implements AdminMenuService {
    @Autowired
    private AdminMenuMapper menuMapper;
    @Autowired
    private AdminRoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private AdminRoleDao roleDao;

    @Override
    public List<AdminMenu> getMenusByUserId(Long userId) {
        List<AdminMenu> menuList;
        // 判断是否是超级管理员
        if (userId.equals(1L)) {
            QueryWrapper<AdminMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("sort");
            menuList = menuMapper.selectList(queryWrapper);
        }else {
            menuList = roleDao.getMenuList(userId);
        }
        return getChildPerms(menuList, 0);
    }

    @Override
    public List<AdminMenu> getMenusByRoleId(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<RouterVo> buildMenus(List<AdminMenu> menus) {
        List<RouterVo> routers = new ArrayList<>();
        menus.forEach(menu -> {
            RouterVo router = new RouterVo();
            router.setName(menu.getName());
            router.setPath(menu.getPath());
            router.setComponent(menu.getComponent());
            if (menu.getRedirect() != null && !"".equals(menu.getRedirect())) {
                router.setRedirect(menu.getRedirect());
            }
            MetaVo meta = BeanUtil.copyProperties(menu, MetaVo.class);
            router.setMeta(meta);
            if (menu.getChildren() != null && menu.getChildren().size() != 0) {
                    router.setAlwaysShow(true);
                    router.setChildren(buildMenus(menu.getChildren()));
                }
                routers.add(router);
            });
        return routers;
    }

    @Override
    public List<AdminMenu> selectAllMenuList() {
        return menuMapper.selectList(null);
    }

    @Override
    public List<AdminMenu> selectMenuList(AdminMenuParam menu) {
        QueryWrapper<AdminMenu> queryWrapper = new QueryWrapper<>();
        if (menu.getTitle() != null) {
            queryWrapper.like("title", menu.getTitle());
        }
        if (menu.getType() != null) {
            queryWrapper.eq("type", menu.getType());
        }
        if (menu.getStartTime() != null && menu.getEndTime() != null) {
            queryWrapper.between("create_time", menu.getStartTime(), menu.getEndTime());
        }
        queryWrapper.orderByAsc("sort");
        List<AdminMenu> menus = menuMapper.selectList(queryWrapper);
        return getChildPerms(menus, 0);
    }

    @Override
    public List<AdminMenu> selectMenuTreeByUserId(Long userId) {
        return  roleDao.getMenuList(userId);
    }

    @Override
    public List<Integer> selectMenuListByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<AdminMenu> buildMenuTree(List<AdminMenu> menus) {
        List<AdminMenu> returnList = new ArrayList<>();
        for (AdminMenu t : menus) {
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0) {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<AdminMenu> menus) {
        List<AdminMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public AdminMenu selectMenuById(Long menuId) {
        return menuMapper.selectById(menuId);
    }

    @Override
    public boolean hasChildByMenuId(Long menuId) {
        return false;
    }

    @Override
    public boolean checkMenuExistRole(Long menuId) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMenu(AdminMenu menu) {
        menu.setCreateTime(new Date());
        return menuMapper.insert(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMenu(AdminMenu menu) {
        if (menu.getId() == null){
            return 0;
        }
        menu.setUpdateTime(new Date());
        return menuMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMenuById(Long menuId) {
        // 删除菜单与角色关联
        roleMenuRelationMapper.delete(new QueryWrapper<AdminRoleMenuRelation>().eq("menu_id", menuId));
        // 删除菜单与子菜单
        menuMapper.delete(new QueryWrapper<AdminMenu>().eq("parent_id", menuId));
        // 删除菜单
        menuMapper.deleteById(menuId);
        return 0;
    }

    @Override
    public int checkMenuNameUnique(AdminMenu menu) {
        return 0;
    }

    /**
     * 根据父节点的ID获取所有子节点
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<AdminMenu> getChildPerms(List<AdminMenu> list, int parentId) {
        List<AdminMenu> returnList = new ArrayList<>();
        for (AdminMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t 传入的父节点
     */
    private void recursionFn(List<AdminMenu> list, AdminMenu t){
        // 得到子节点列表
        List<AdminMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (AdminMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                for (AdminMenu n : childList) {
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<AdminMenu> getChildList(List<AdminMenu> list, AdminMenu t) {
        List<AdminMenu> tList = new ArrayList<>();
        for (AdminMenu menu : list) {
            if (menu.getParentId().equals(t.getId())) {
                tList.add(menu);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<AdminMenu> list, AdminMenu t) {
        return getChildList(list, t).size() > 0;
    }
}
