package com.heima.user.feign;

/**
 * @Author Administrator
 * @create 2021/1/30 23:20
 */

import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 文章.作者-远程接口
 */

@FeignClient("leadnews-article")
public interface ArticleFeign {

    /**
     * POST
     * 保存作者信息
     *
     * @param apAuthor
     * @return
     */
    @PostMapping("/api/v1/author/save")
    public ResponseResult save(@RequestBody ApAuthor apAuthor);

    /**
     * GET
     * 根据用户id查询作者信息
     *
     * @param id
     * @return
     */
    @GetMapping("/api/v1/author/findByUserId/{id}")
    public ApAuthor queryByUserId(@PathVariable("id") Integer id);
}
