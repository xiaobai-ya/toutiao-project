package com.heima.api.admin;

import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author Administrator
 * @create 2021/1/27 22:59
 */

/**
 * 敏感词接口-controller
 */

@Api(value = "敏感词设置", tags = "sensitive", description = "敏感词设置API")  //swagger 修饰整个类，描述Controller的作用
public interface AdSensitiveControllerApi {

    /**
     * 敏感词列表查询- 模糊.分页
     * @param dto 前端json封装
     * @return
     */
    @ApiOperation("根据名称分页查询敏感词列表")
    public ResponseResult queryByNameAndPage(SensitiveDto dto);


    /**
     * 添加敏感词信息
     * @param sensitive 敏感词对象pojo
     * @return
     */
    @ApiOperation("添加敏感词信息")
    public ResponseResult save(AdSensitive sensitive);

    /**
     * 根据id 修改敏感词信息
     * @param sensitive 敏感词对象
     * @return
     */
    @ApiOperation("根据id 修改敏感词信息")
    public ResponseResult update(AdSensitive sensitive);

    /**
     * 根据id删除敏感词信息
     * @param id 敏感词id
     * @return
     */
    @ApiOperation("根据id删除敏感词信息")
    public ResponseResult deleteById(Integer id);
}
