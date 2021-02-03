package com.heima.api.wemedia;

/**
 * @Author Administrator
 * @create 2021/2/2 0:09
 */

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 自媒体登录接口-controller
 */

@Api(value = "自媒体登录", tags = "leadnews-wemedia", description = "自媒体登录API")  //swagger 修饰整个类，描述Controller的作用
public interface LoginControllerApi {

    /**
     * 自媒体登录
     * @param dto
     * @return
     */
    @ApiOperation("自媒体登录")
    public ResponseResult login(WmUserDto dto);
}
