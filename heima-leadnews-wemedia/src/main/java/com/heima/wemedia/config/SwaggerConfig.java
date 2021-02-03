package com.heima.wemedia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Administrator
 * @create 2021/1/27 22:45
 */
@Configuration
@ComponentScan("com.heima.common.knife4j")  //扫描配置类 common工程中的包
public class SwaggerConfig {
}
