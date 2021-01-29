package com.heima.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @create 2021/1/29 21:49
 */

/**|
 * 管理员用户名密码-前端json封装
 */

@Data
public class AdUserDto {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")    //swagger用对象接收参数时，描述对象的一个字段
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty("密码")    //swagger用对象接收参数时，描述对象的一个字段
    private String password;
}
