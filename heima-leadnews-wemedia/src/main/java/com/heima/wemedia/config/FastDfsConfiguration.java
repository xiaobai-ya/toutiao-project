package com.heima.wemedia.config;

/**
 * @Author Administrator
 * @create 2021/2/2 0:04
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * fastDFS配置类
 */

@Configuration
@ComponentScan("com.heima.common.fastdfs")  //扫包配置  common模块
public class FastDfsConfiguration {
}
