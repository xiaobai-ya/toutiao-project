package com.heima.admin.service;

/**
 * @Author Administrator
 * @create 2021/1/26 19:45
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 频道-业务接口
 */

public interface AdChannelService extends IService<AdChannel> {

    /**
     * 频道查询-分页查询列表
     * @param dto 对前端数据封装对象
     * @return
     */
    public ResponseResult queryByNameAndPage(ChannelDto dto);

    /**
     * 保存频道
     * @param channel 频道pojo对象
     * @return
     */
    public ResponseResult insert(AdChannel channel);

    /**
     * 根据id更新频道
     * @param channel 频道pojo对象
     * @return
     */
    public ResponseResult update(AdChannel channel);

    /**
     * 根据id删除频道
     * @param id 频道id
     * @return
     */
    public ResponseResult deleteById(Integer id);
}
