package com.heima.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 集中初始化了扫包配置
 */

@Configuration
@ComponentScan({"com.heima.common.aliyun","com.heima.common.exception"
        ,"com.heima.common.fastdfs","com.heima.common.knife4j"})
public class InitConfig {
}
