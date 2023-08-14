package com.htwokey.htwokeysecurity.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * <p>动态权限访问决策管理器</p>
 * @author hchbo
 * @date 2023/6/16 15:45
 */
@Component
@Slf4j
public class DynamicAccessDecisionManager implements AuthorizationManager<RequestAuthorizationContext> {


    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        // 获取当前用户名称
        String username = authentication.get().getName();
        // 如果是超级管理员，直接放行
        if ("admin".equals(username)) {
            return new AuthorizationDecision(true);
        }
        // 请求路径
        String requestUri = object.getRequest().getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 获取当前用户的所有权限
        Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
        // 判断当前用户是否有权限
        return authorities.stream()
                .filter(authority -> pathMatcher.match(authority.getAuthority(), requestUri))
                .findAny()
                .map(authority -> new AuthorizationDecision(true))
                .orElse(new AuthorizationDecision(false));
    }
}
