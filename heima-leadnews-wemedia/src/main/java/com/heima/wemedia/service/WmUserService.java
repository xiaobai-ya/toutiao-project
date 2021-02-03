package com.heima.wemedia.service;

/**
 * @Author Administrator
 * @create 2021/1/30 21:53
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmUserDto;
import com.heima.model.media.pojos.WmUser;

/**
 * 自媒体-业务层接口
 */

public interface WmUserService extends IService<WmUser> {

    /**
     * 登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmUserDto dto);
}
