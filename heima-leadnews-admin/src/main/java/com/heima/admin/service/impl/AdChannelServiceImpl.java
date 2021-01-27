package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdChannelMapper;
import com.heima.admin.service.AdChannelService;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author Administrator
 * @create 2021/1/26 19:50
 */

/**
 * 频道-业务接口实现
 */
@Service
public class AdChannelServiceImpl extends ServiceImpl<AdChannelMapper, AdChannel> implements AdChannelService  {
    /**
     * 查询频道数据列表
     * @param dto 对前端数据封装对象
     * @return
     */
    @Override
    public ResponseResult queryByNameAndPage(ChannelDto dto) {
        /*参数校验*/
        if (dto!=null){

            /*校验分页参数,如果为空替换默认值*/
            dto.checkParam();

            /*数据查询,条件构建,分页对象构建*/
            Page<AdChannel> page = new Page<>(dto.getPage(),dto.getSize());
            LambdaQueryWrapper<AdChannel> queryWrapper =new LambdaQueryWrapper<>();

            /*如果,频道名称为空,查询全部,不为空,模糊查询*/
            if (StringUtils.isNotEmpty(dto.getName())){

                /**
                 * 构建模糊查询条件
                 * AdChannel::getName   匹配pojo列名
                 * dto.getName()   获取前端模糊查询值
                 */
                queryWrapper.like(AdChannel::getName,dto.getName());

            }

            IPage<AdChannel> result = super.page(page, queryWrapper);


            /*数据封装  多态*/
            ResponseResult ResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), Integer.parseInt(String.valueOf(result.getTotal())));
            /*设置结果数据*/
            ResponseResult.setData(result.getRecords());

            /*操作成功*/
            return ResponseResult;

        }

        /*参数错误*/
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    /**
     * 保存频道数据
     * @param channel 频道pojo对象
     * @return
     */
    @Override
    public ResponseResult insert(AdChannel channel) {

        /*数据校验*/
        if (channel!=null){

            /*设置数据创建时间*/
            channel.setCreatedTime(new Date());
            super.save(channel);

            /*添加成功*/
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        /*参数错误*/
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    /**
     * 根据id更新频道数据
     * @param channel 频道pojo对象
     * @return
     */
    @Override
    public ResponseResult update(AdChannel channel) {

        /*参数校验*/
        if (channel!=null && channel.getId() !=null){

            super.updateById(channel);

            /*操作成功*/
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        /*参数错误*/
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    /**
     * 删除频道信息
     * 注意:只能删除状态已经禁用的频道!!!
     * @param id 频道id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id) {

        /*参数校验*/
        if (id!=null){

            /*验证频道数据是否存在*/
            AdChannel one = super.getById(id);
            if (one!=null){
                /*数据存在,检查改频道状态*/

                if (!one.getStatus()){
                    /*频道为禁用状态,删除*/
                    super.removeById(id);

                    /*操作成功*/
                    return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
                }

                /*频道未禁用*/
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"该频道正在使用,请先禁用该频道!");

            }

            /*数据不存在*/
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);

        }

        /*参数错误*/
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }
}