package com.htwokey.htwokeysecurity.filter;

import com.alibaba.fastjson.JSONObject;
import com.htwokey.htwokeysecurity.entity.AdminOperationLog;
import com.htwokey.htwokeysecurity.service.AdminOperationLogService;
import com.htwokey.htwokeysecurity.utils.JwtTokenUtil;
import com.htwokey.htwokeysecurity.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Date;


/**
 * @author hchbo
 * @since 2023/5/10 9:51
 * <p>操作记录AOP</p>
 */
@Aspect
@Component
@Slf4j
public class OperationRecordAspect {

    private static final String POINT_CUT = "execution(public * com.htwokey.htwokeysecurity.controller.*.*(..))";

    private final JwtTokenUtil jwtTokenUtil;

    private final AdminOperationLogService operationLogService;
    @Autowired
    public OperationRecordAspect(JwtTokenUtil jwtTokenUtil, AdminOperationLogService operationLogService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.operationLogService = operationLogService;
    }
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 切入点
     */
    @Pointcut(POINT_CUT)
    public void pointCut() {
    }

    /**
     * 获取请求参数并保存
     */
    @Before("pointCut()")
    public void getRequestInfo(JoinPoint joinPoint){
        // 获取servletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 获取请求参数
        String ip = RequestUtil.getRequestIp(request);
        String userAgent = request.getHeader("User-Agent");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String params;
        String contentType = request.getContentType();
        if ("GET".equals(method)) {
            params = request.getQueryString();
        } else if ("POST".equals(method) && contentType != null && contentType.contains("multipart/form-data")) {
            // 文件上传参数不保存
            params = "文件上传";
        } else if(requestURI.contains("/admin/login")) {
            // 登录参数不保存
            params = "登录";
        }else {
            params = JSONObject.toJSONString(joinPoint.getArgs());
        }

        // 处理token
        String authHeader = request.getHeader("Authorization");
        String username = "";
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            var authToken = authHeader.substring(this.tokenHead.length());
            // 从token获取用户名
            username = jwtTokenUtil.getUserNameFromToken(authToken);
        }
        // 封装操作日志
        AdminOperationLog operationLog = new AdminOperationLog();
        operationLog.setUsername(username);
        operationLog.setIp(ip);
        operationLog.setMethod(method);
        operationLog.setParams(params);
        operationLog.setUrl(requestURI);
        operationLog.setUserAgent(userAgent);
        operationLog.setCreateTime(new Date());
        // 保存操作日志
        operationLogService.save(operationLog);
    }

}
