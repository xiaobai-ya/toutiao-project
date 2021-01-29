package com.heima.admin.service;

/**
 * @Author Administrator
 * @create 2021/1/29 21:56
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 管理员用户登录-业务接口
 */


public interface UserLoginService extends IService<AdUser> {

    /**
     * 管理员用户登录校验
     * @param dto
     * @return
     */
    public ResponseResult login(AdUserDto dto);
}
