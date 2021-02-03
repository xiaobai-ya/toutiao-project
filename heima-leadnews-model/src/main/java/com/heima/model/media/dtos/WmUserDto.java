package com.heima.model.media.dtos;

import lombok.Data;

/**
 * 自媒体登录,前端json封装
 */

@Data
public class WmUserDto {

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;
}