package com.htwokey.htwokeysecurity.entity.bo;

import com.htwokey.htwokeysecurity.entity.AdminResource;
import com.htwokey.htwokeysecurity.entity.AdminRole;
import com.htwokey.htwokeysecurity.entity.AdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hchbo
 * @date 2023/3/29 11:43
 */
public class AdminUserDetails implements UserDetails {
    /**后台用户*/
    private final AdminUser user;
    /**拥有资源列表*/
    private final List<AdminResource> resourceList;

    public AdminUserDetails(AdminUser user, List<AdminResource> resourceList) {
        this.user = user;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(1);
    }
}
