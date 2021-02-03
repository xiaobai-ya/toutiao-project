package com.heima.wemedia.controller.v1;

/**
 * @Author Administrator
 * @create 2021/2/2 10:03
 */

import com.heima.api.wemedia.WmNewsControllerApi;
import com.heima.common.constants.wemedia.WemediaContans;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmNewsDto;
import com.heima.model.media.dtos.WmNewsPageReqDto;
import com.heima.model.media.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 自媒体文章管理-控制层
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController implements WmNewsControllerApi {

    @Autowired
    private WmNewsService wmNewsService;


    /**
     * POST
     * 分页带条件查询自媒体文章列表
     *
     * @param wmNewsPageReqDto
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto wmNewsPageReqDto) {
        return wmNewsService.queryAll(wmNewsPageReqDto);
    }

    /**
     * POST
     * 提交文章-修改-草稿
     *
     * @param dto status:状态 提交为1  草稿为0
     * WmNews.Status.SUBMIT.getCode()  :内部枚举
     * @return
     */
    @PostMapping("/submit")
    @Override
    public ResponseResult summitNews(@RequestBody WmNewsDto dto) {

        if (WmNews.Status.SUBMIT.getCode() == dto.getStatus()){
            /*提交*/
            return wmNewsService.saveNews(dto,WmNews.Status.SUBMIT.getCode());
        }else {
            /*保存草稿*/
            return wmNewsService.saveNews(dto,WmNews.Status.NORMAL.getCode());
        }
    }

    /**
     * GET
     * 根据id查询文章-回显
     * @param id
     * @return
     */
    @GetMapping("/one/{id}")
    @Override
    public ResponseResult queryWmNewsById(@PathVariable("id") Integer id) {
        return wmNewsService.queryWmNewsById(id);
    }

    /**
     * GET
     * 根据id删除文章
     * @param id
     * @return
     */
    @GetMapping("/del_news/{id}")
    @Override
    public ResponseResult delNews(@PathVariable("id") Integer id) {
        return wmNewsService.delNews(id);
    }

    /**
     * POST
     * 文章上下架
     * @param dto
     * @return
     */
    @PostMapping("/down_or_up")
    @Override
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto) {
        return wmNewsService.downOrUp(dto);
    }
}
