package com.heima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.SensitiveDto;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @Author Administrator
 * @create 2021/1/27 23:12
 */

/**
 * 敏感词-业务接口
 */

public interface AdSensitiveService extends IService<AdSensitive> {

    /**
     * 敏感词列表-模糊
     * @param dto 前端json数据封装对象
     * @return
     */
    public ResponseResult queryByNameAndPage(SensitiveDto dto);

    /**
     * 添加敏感词信息
     * @param sensitive 敏感词对象pojo
     * @return
     */
    public ResponseResult insert(AdSensitive sensitive);

    /**
     * 根据id更新敏感词信息
     * @param sensitive 敏感词对象pojo
     * @return
     */
    public ResponseResult update(AdSensitive sensitive);

    /**
     * 根据id删除敏感词信息
     * @param id 敏感词id
     * @return
     */
    public ResponseResult deleteById(Integer id);
}
