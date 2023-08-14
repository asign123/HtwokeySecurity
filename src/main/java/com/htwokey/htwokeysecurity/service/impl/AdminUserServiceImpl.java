package com.htwokey.htwokeysecurity.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.htwokey.htwokeysecurity.dao.AdminUserRoleRelationDao;
import com.htwokey.htwokeysecurity.entity.*;
import com.htwokey.htwokeysecurity.entity.bo.AdminUserDetails;
import com.htwokey.htwokeysecurity.entity.dto.AdminUserParam;
import com.htwokey.htwokeysecurity.entity.dto.UpdateAdminUserPasswordParam;
import com.htwokey.htwokeysecurity.exception.Asserts;
import com.htwokey.htwokeysecurity.mapper.AdminUserLoginLogMapper;
import com.htwokey.htwokeysecurity.mapper.AdminUserMapper;
import com.htwokey.htwokeysecurity.mapper.AdminUserRoleRelationMapper;
import com.htwokey.htwokeysecurity.service.AdminCacheService;
import com.htwokey.htwokeysecurity.service.AdminUserService;
import com.htwokey.htwokeysecurity.utils.JwtTokenUtil;
import com.htwokey.htwokeysecurity.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hchbo
 * @date 2023/4/14 10:41
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AdminUserRoleRelationDao adminUserRoleRelationDao;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminUserRoleRelationMapper adminUserRoleRelationMapper;
    @Autowired
    private AdminUserLoginLogMapper loginLogMapper;

    @Override
    public AdminUser getAdminByUsername(String username) {
        AdminUser user = getCacheService().getAdmin(username);
        if(user!=null) {
            return  user;
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        user = adminUserMapper.selectOne(queryWrapper);
        if(user!=null){
            getCacheService().setAdmin(user);
            return user;
        }
        return null;
    }

    @Override
    public AdminUser register(AdminUserParam userParam) {
        AdminUser user = new AdminUser();
        BeanUtils.copyProperties(userParam, user);
        user.setCreateTime(new Date());
        user.setStatus(1);
        //查询是否有相同用户名的用户
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        List<AdminUser> umsAdminList = adminUserMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(umsAdminList)) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        adminUserMapper.insert(user);
        return user;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        AdminUser user = getAdminByUsername(username);
        if(user==null) {
            return;
        }
        AdminUserLoginLog loginLog = new AdminUserLoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLog.setUserAgent(request.getHeader("User-Agent"));
        loginLogMapper.insert(loginLog);
    }


    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public AdminUser getItem(Long id) {
        return adminUserMapper.selectById(id);
    }

    @Override
    public List<AdminUser> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","username","nick_name","icon","email","note","create_time","status");
        if(StrUtil.isNotEmpty(keyword)){
            queryWrapper.like("username",keyword);
            queryWrapper.or();
            queryWrapper.like("nick_name",keyword);
        }
        return adminUserMapper.selectList(queryWrapper);
    }

    @Override
    public int update(AdminUser admin) {
        AdminUser user = adminUserMapper.selectById(admin.getId());
        if(user.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        int count = adminUserMapper.updateById(admin);
        getCacheService().delAdmin(admin.getId());
        return count;
    }

    @Override
    public int delete(Long id) {
        //不能删除超级管理员
        if (id == 1L){
            return 0;
        }
        getCacheService().delAdmin(id);
        int count = adminUserMapper.deleteById(id);
        getCacheService().delResourceList(id);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(Long userId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<AdminUserRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        adminUserRoleRelationMapper.delete(queryWrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AdminUserRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AdminUserRoleRelation roleRelation = new AdminUserRoleRelation();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminUserRoleRelationDao.insertList(list);
        }
        getCacheService().delResourceList(userId);
        return count;
    }

    @Override
    public List<AdminRole> getRoleList(Long adminId) {
        return adminUserRoleRelationDao.getRoleList(adminId);
    }

    @Override
    public List<AdminResource> getResourceList(Long userId) {
        List<AdminResource> resourceList = getCacheService().getResourceList(userId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        resourceList = adminUserRoleRelationDao.getResourceList(userId);
        if(CollUtil.isNotEmpty(resourceList)){
            getCacheService().setResourceList(userId,resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdateAdminUserPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",param.getUsername());
        List<AdminUser> adminList = adminUserMapper.selectList(queryWrapper);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        AdminUser user = adminList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),user.getPassword())){
            return -3;
        }
        user.setPassword(passwordEncoder.encode(param.getNewPassword()));
        adminUserMapper.updateById(user);
        getCacheService().delAdmin(user.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        AdminUser user = getAdminByUsername(username);
        if (user != null) {
            List<AdminResource> resourceList = getResourceList(user.getId());
            return new AdminUserDetails(user,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public AdminCacheService getCacheService() {
        return SpringUtil.getBean(AdminCacheService.class);
    }
}
