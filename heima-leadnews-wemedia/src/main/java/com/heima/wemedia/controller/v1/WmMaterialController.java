package com.heima.wemedia.controller.v1;

import com.heima.api.wemedia.WmMaterialControllerApi;
import com.heima.common.constants.wemedia.WemediaContans;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传控制层
 */

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController implements WmMaterialControllerApi {
    @Autowired
    private WmMaterialService wmMaterialService;

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload_picture")
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    @RequestMapping("/list")
    @Override
    public ResponseResult queryList(@RequestBody WmMaterialDto dto) {
        return wmMaterialService.queryList(dto);
    }

    /**
     * 删除素材图片
     * @param id
     * @return
     */
    @GetMapping("/del_picture/{id}")
    @Override
    public ResponseResult delPicture(@PathVariable("id") Integer id) {
        return wmMaterialService.delPicture(id);
    }

    /**
     * 取消收藏图片
     * @param id
     * @return
     */
    @GetMapping("/cancel_collect/{id}")
    @Override
    public ResponseResult cancelCollectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updatePictureStatus(id, WemediaContans.CANCEL_COLLECT_MATERIAL);
    }

    /**
     * 收藏图片
     * @param id
     * @return
     */
    @GetMapping("/collect/{id}")
    @Override
    public ResponseResult collectionMaterial(@PathVariable("id") Integer id) {
        return wmMaterialService.updatePictureStatus(id, WemediaContans.COLLECT_MATERIAL);

    }

}