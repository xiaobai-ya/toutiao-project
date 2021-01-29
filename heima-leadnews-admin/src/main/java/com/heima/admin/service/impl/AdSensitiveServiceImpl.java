package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdSensitiveMapper;
import com.heima.admin.service.AdSensitiveService;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author Administrator
 * @create 2021/1/27 23:19
 */
@Service
public class AdSensitiveServiceImpl extends ServiceImpl<AdSensitiveMapper,AdSensitive> implements AdSensitiveService {

    /**
     * 查询敏感词列表 -模糊
     * @param dto 前端json数据封装对象
     * @return
     */
    @Override
    public ResponseResult queryByNameAndPage(SensitiveDto dto) {

        /*参数校验*/
        if (dto==null){
            /*无效参数*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        /*分页参数校验*/
        dto.checkParam();

        /*构建分页查询条件*/
        Page<AdSensitive> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<AdSensitive> queryWrapper = new LambdaQueryWrapper<>();

        /*如果不为空,就模糊查询*/
        if (StringUtils.isNotEmpty(dto.getName())){
            /*名称模糊查询*/
            queryWrapper.like(AdSensitive::getSensitives,dto.getName());
        }

        IPage<AdSensitive> result = super.page(page, queryWrapper);

        ResponseResult responseResult =new PageResponseResult(dto.getPage(),dto.getSize(),(int)result.getTotal());

        /*返回数据填充*/
        responseResult.setData(result.getRecords());

        return responseResult;
    }

    /**
     * 插入敏感词数据
     * @param sensitive 敏感词对象pojo
     * @return
     */
    @Override
    public ResponseResult insert(AdSensitive sensitive) {

        /*参数校验*/
        if (sensitive==null){
            /*无效参数*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        /*设置数据创建时间*/
        sensitive.setCreatedTime(new Date());
        super.save(sensitive);

        /*添加成功*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 根据id更新敏感词数据
     * @param sensitive 敏感词对象pojo
     * @return
     */
    @Override
    public ResponseResult update(AdSensitive sensitive) {

        /*参数校验*/
        if (sensitive==null || sensitive.getId()==null){
            /*无效参数*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        super.updateById(sensitive);

        /*更新成功*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 根据id删除敏感词信息
     * @param id 敏感词id
     * @return
     */
    @Override
    public ResponseResult deleteById(Integer id) {

        if (id==null){
            /*无效参数*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        super.removeById(id);

        /*删除成功*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
