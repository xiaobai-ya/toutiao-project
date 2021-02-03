package com.heima.api.wemedia;

/**
 * @Author Administrator
 * @create 2021/1/30 21:46
 */

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.pojos.WmUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 自媒体接口-controller
 */

@Api(value = "自媒体用户", tags = "leadnews-wemedia", description = "自媒体用户API")  //swagger 修饰整个类，描述Controller的作用
public interface WmUserControllerApi {

    /**
     * 保存自媒体用户
     * @param wmUser
     * @return
     */
    @ApiOperation("保存自媒体用户")
    public ResponseResult save(WmUser wmUser);

    /**
     * 根据名称查询自媒体用户
     * @param name
     * @return
     */
    @ApiOperation("根据名称查询自媒体用户")
    public WmUser queryByName(String name);
}
