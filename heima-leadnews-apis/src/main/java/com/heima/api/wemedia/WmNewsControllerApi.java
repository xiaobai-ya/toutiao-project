package com.heima.api.wemedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmNewsDto;
import com.heima.model.media.dtos.WmNewsPageReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 自媒体-文章接口-controller
 */

@Api(value = "自媒体文章管理", tags = "leadnews-wemedia", description = "自媒体文章管理API")  //swagger 修饰整个类，描述Controller的作用
public interface WmNewsControllerApi {

    /**
     * 分页带条件查询自媒体文章列表
     * @param wmNewsPageReqDto
     * @return
     */
    @ApiOperation("分页带条件查询自媒体文章列表")
    public ResponseResult findAll(WmNewsPageReqDto wmNewsPageReqDto);

    /**
     * 提交文章
     * @param wmNews
     * @return
     */
    @ApiOperation("提交文章-草稿-修改")
    ResponseResult summitNews(WmNewsDto wmNews);

    /**
     * 根据id获取文章信息 -回显
     * @return
     */
    @ApiOperation("根据id获取文章信息")
    public ResponseResult queryWmNewsById(Integer id);

    /**
     * 根据id删除文章
     * @return
     */
    @ApiOperation("根据id删除文章")
    public ResponseResult delNews(Integer id);

    /**
     * 文章上下架
     * @param dto
     * @return
     */
    @ApiOperation("文章上下架")
    public ResponseResult downOrUp(WmNewsDto dto);
}