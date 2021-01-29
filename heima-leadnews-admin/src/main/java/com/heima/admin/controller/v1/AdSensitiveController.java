package com.heima.admin.controller.v1;

import com.heima.admin.service.AdSensitiveService;
import com.heima.api.admin.AdSensitiveControllerApi;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @create 2021/1/27 23:55
 */
@RestController
@RequestMapping("/api/v1/sensitive")
public class AdSensitiveController implements AdSensitiveControllerApi {

    @Autowired
    private AdSensitiveService adSensitiveService;

    /**
     * POST
     * 查询敏感词列表 -模糊查询
     * @param dto 前端json封装
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult queryByNameAndPage(@RequestBody SensitiveDto dto) {
        return adSensitiveService.queryByNameAndPage(dto);
    }

    /**
     * POST
     * 新增敏感词信息
     * @param sensitive 敏感词对象pojo
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody AdSensitive sensitive) {
        return adSensitiveService.insert(sensitive);
    }

    /**
     * POST
     * 根据id更新敏感词信息
     * @param sensitive 敏感词对象
     * @return
     */
    @PostMapping("/update")
    @Override
    public ResponseResult update(@RequestBody AdSensitive sensitive) {
        return adSensitiveService.update(sensitive);
    }

    /**
     * DELETE
     * 根据id删除敏感词信息
     * @param id 敏感词id
     * @return
     */
    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        return adSensitiveService.deleteById(id);
    }
}
