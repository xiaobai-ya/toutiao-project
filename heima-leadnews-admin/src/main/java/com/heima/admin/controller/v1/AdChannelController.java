package com.heima.admin.controller.v1;

import com.heima.admin.service.AdChannelService;
import com.heima.api.admin.AdChannelControllerApi;
import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @create 2021/1/26 20:32
 */

/**
 * 频道-控制层
 */

@RestController
@RequestMapping("/api/v1/channel")
public class AdChannelController implements AdChannelControllerApi {


    @Autowired
    private AdChannelService adChannelService;

    /**
     * POST
     * 查询频道列表
     * @param dto 前段json封装
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult queryByNameAndPage(@RequestBody ChannelDto dto) {
        return adChannelService.queryByNameAndPage(dto);
    }

    /**
     * POST
     * 保存频道
     * @param channel 频道对象
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody AdChannel channel) {
        return adChannelService.insert(channel);
    }

    /**
     * PUT
     * 更新频道
     * @param channel 频道对象
     * @return
     */
    @PostMapping("/update")
    @Override
    public ResponseResult update(@RequestBody AdChannel channel) {
        return adChannelService.update(channel);
    }

    /**
     * 删除频道
     * @param id 频道id
     * @return
     */
    @GetMapping("/del/{id}")
    @Override
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return adChannelService.deleteById(id);
    }
}