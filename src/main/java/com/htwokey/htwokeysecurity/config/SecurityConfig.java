package com.htwokey.htwokeysecurity.config;

import com.htwokey.htwokeysecurity.component.DynamicAccessDecisionManager;
import com.htwokey.htwokeysecurity.component.RestAuthenticationEntryPoint;
import com.htwokey.htwokeysecurity.component.RestfulAccessDeniedHandler;
import com.htwokey.htwokeysecurity.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author hchbo
 * @date 2023/3/29 11:40
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final AuthenticationProvider authenticationProvider;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final DynamicAccessDecisionManager dynamicAccessDecisionManager;

    public SecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                          AuthenticationProvider authenticationProvider,
                          RestfulAccessDeniedHandler restfulAccessDeniedHandler,
                          RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                          DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.authenticationProvider = authenticationProvider;
        this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.dynamicAccessDecisionManager = dynamicAccessDecisionManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 禁用 CSRF
        http.csrf().disable()
                // 设置白名单
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/admin/login").permitAll()
                .anyRequest().access(dynamicAccessDecisionManager)
                // 禁用缓存
                .and().headers().cacheControl().disable()
                // 禁用 session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 添加身份验证
                .and().authenticationProvider(authenticationProvider)
                // 自定义权限拒绝处理类
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 自定义权限拦截器JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
