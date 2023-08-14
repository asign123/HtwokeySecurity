package com.htwokey.htwokeysecurity.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hchbo
 * @date 2023/3/29 14:17
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.htwokey.htwokeysecurity.mapper","com.htwokey.htwokeysecurity.dao"})
public class MybatisConfig {

}
