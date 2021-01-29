package com.heima.api.admin;

/**
 * @Author Administrator
 * @create 2021/1/29 21:53
 */

import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理员登录接口-controller
 */

@Api(value = "管理员登录", tags = "login-admin", description = "管理员登录API")  //swagger 修饰整个类，描述Controller的作用
public interface LoginControllerApi {

    /**
     * 登录校验
     * @param adUserDto
     * @return
     */
    @ApiOperation("登录校验")
    public ResponseResult login(AdUserDto adUserDto);
}
