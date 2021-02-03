package com.heima.wemedia.config;

/**
 * @Author Administrator
 * @create 2021/1/27 10:10
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常配置类
 * 因包名不一致,配置扫包
 */

@Configuration
@ComponentScan("com.heima.common.exception")
public class ExceptionCatchConfig {
}
