package com.heima.wemedia.service;

/**
 * @Author Administrator
 * @create 2021/2/2 9:37
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmNewsDto;
import com.heima.model.media.dtos.WmNewsPageReqDto;
import com.heima.model.media.pojos.WmNews;

/**
 * 自媒体文章业务接口
 */

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询文章列表
     * @param dto
     * @return
     */
    public ResponseResult queryAll(WmNewsPageReqDto dto);

    /**
     * 自媒体文章发布
     * @param wmNews
     * @param isSubmit  是否为提交 1 为提交 0为草稿
     * @return
     */
    public ResponseResult saveNews(WmNewsDto wmNews, Short isSubmit);

    /**
     * 根据id查询文章
     * @param id 文章id
     * @return
     */
    public ResponseResult queryWmNewsById(Integer id);

    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    public ResponseResult delNews(Integer id);

    /**
     * 文章的上下架操作
     * @param dto
     * @return
     */
    public ResponseResult downOrUp(WmNewsDto dto);
}
