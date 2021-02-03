package com.heima.api.article;

/**
 * @Author Administrator
 * @create 2021/1/30 22:31
 */

import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文章-作者接口-controller
 */

@Api(value = "文章-作者", tags = "leadnews-article", description = "文章-作者API")  //swagger 修饰整个类，描述Controller的作用
public interface AuthorControllerApi {

    /**
     * 保存作者信息
     * @param apAuthor
     * @return
     */
    @ApiOperation("保存作者信息")
    public ResponseResult save(ApAuthor apAuthor);

    /**
     * 根据用户id查询作者信息
     * @param id
     * @return
     */
    @ApiOperation("根据用户id查询作者信息")
    public ApAuthor queryByUserId(Integer id);
}
