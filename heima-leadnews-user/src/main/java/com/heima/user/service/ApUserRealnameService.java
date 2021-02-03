package com.heima.user.service;

/**
 * @Author Administrator
 * @create 2021/1/30 20:28
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUserRealname;

/**
 * 用户认证-业务接口
 */

public interface ApUserRealnameService extends IService<ApUserRealname> {

    /**
     * 根据状态,分页查询用户认证列表
     * @param dto
     * @return
     */
    public ResponseResult loadListByStatus(AuthDto dto);

    /**
     * 根据状态进行审核.更新状态
     * @param dto
     * @param status
     * @return
     */
    public ResponseResult updateStatusById(AuthDto dto,Short status);
}
