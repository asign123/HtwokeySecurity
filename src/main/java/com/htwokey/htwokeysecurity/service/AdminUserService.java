package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminRole;
import com.htwokey.htwokeysecurity.entity.AdminUser;
import com.htwokey.htwokeysecurity.entity.dto.AdminUserParam;
import com.htwokey.htwokeysecurity.entity.dto.UpdateAdminUserPasswordParam;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 *     后台用户表 服务接口
 * </p>
 * @author hchbo
 * &#064;date  2023/4/14 10:12
 */
public interface AdminUserService {

    /**
     * 根据用户名获取后台管理员
     * @param username 用户名
     * @return 后台管理员
     */
    AdminUser getAdminByUsername(String username);

    /**
     * 注册功能
     * @param umsAdminParam 注册参数
     * @return 注册结果
     */
    AdminUser register(AdminUserParam umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     * @return 新的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     * @param id 用户id
     * @return 用户
     */
    AdminUser getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     * @param keyword 用户名或昵称
     * @param pageSize 分页大小
     * @param pageNum 分页数
     * @return 用户列表
     */
    List<AdminUser> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     * @param admin 用户信息
     * @return 修改结果
     */
    int update(AdminUser admin);

    /**
     * 删除指定用户
     * @param id 用户id
     * @return 删除结果
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     * @param userId 用户id
     * @param roleIds 角色id
     * @return 修改结果
     */

    int updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对应角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<AdminRole> getRoleList(Long userId);

    /**
     * 获取指定用户的可访问资源
     * @param userId 用户id
     * @return 资源列表
     */
    List<AdminResource> getResourceList(Long userId);

    /**
     * 修改密码
     * @param updatePasswordParam 修改密码参数
     * @return 修改结果
     */
    int updatePassword(UpdateAdminUserPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取缓存服务
     * @return 缓存服务
     */
    AdminCacheService getCacheService();
}
