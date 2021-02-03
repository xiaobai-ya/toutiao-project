package com.heima.api.user;

/**
 * @Author Administrator
 * @create 2021/1/30 20:20
 */

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户认证接口-controller
 */

@Api(value = "用户认证", tags = "leadnews-user", description = "用户认证API")  //swagger 修饰整个类，描述Controller的作用
public interface ApUserRealnameControllerApi {


    /**
     * 根据状态,查询用户认证列表
     * @param dto
     * @return
     */
    @ApiOperation("根据状态,查询用户认证列表")
    public ResponseResult loadListByStatus(AuthDto dto);

    /**
     * 审核通过
     * @return
     */
    @ApiOperation("审核通过")
    public ResponseResult authPass(AuthDto dto);

    /**
     * 审核失败-驳回
     * @return
     */
    @ApiOperation("审核失败-驳回")
    public ResponseResult authFail(AuthDto dto);
}
