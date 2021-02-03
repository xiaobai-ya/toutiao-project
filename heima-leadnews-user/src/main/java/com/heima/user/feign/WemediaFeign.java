package com.heima.user.feign;

/**
 * @Author Administrator
 * @create 2021/1/30 23:15
 */

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.pojos.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 自媒体远程接口
 */

@FeignClient("leadnews-wemedia")
public interface WemediaFeign {

    /**
     * POST
     * 保存自媒体信息
     * @param wmUser
     * @return
     */
    @PostMapping("/api/v1/user/save")
    public ResponseResult save(@RequestBody WmUser wmUser);

    /**
     * GET
     * 根据名称查询自媒体信息
     * @param name
     * @return
     */
    @GetMapping("/api/v1/user/findByName/{name}")
    public WmUser queryByName(@PathVariable("name") String name);
}
