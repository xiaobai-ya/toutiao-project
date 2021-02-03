package com.heima.api.admin;

/**
 * @Author Administrator
 * @create 2021/1/26 17:08
 */

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 频道接口-controller
 */
@Api(value = "频道管理", tags = "channel", description = "频道管理API")  //swagger 修饰整个类，描述Controller的作用
public interface AdChannelControllerApi {

    /**
     * 频道查询-根据名称分页查询
     * @param dto 前段json封装
     * @return
     */
    @ApiOperation("根据名称分页查询频道列表")  //描述一个类的一个方法，或者说一个接口
    public ResponseResult queryByNameAndPage(ChannelDto dto);

    /**
     * 保存频道
     * @param channel 频道对象
     * @return
     */
    @ApiOperation("添加频道信息")
    public ResponseResult save(AdChannel channel);

    /**
     * 根据id修改频道
     * @param channel 频道对象
     * @return
     */
    @ApiOperation("根据对象id更新频道信息")
    public ResponseResult update(AdChannel channel);

    /**
     * 根据id删除频道
     * @param id 频道id
     * @return
     */
    @ApiOperation("根据id删除频道信息")
    public ResponseResult deleteById(Integer id);

    /**
     * 查询所有频道
     * @return
     */
    @ApiOperation("查询所有频道")
    public ResponseResult queryAll();
}
