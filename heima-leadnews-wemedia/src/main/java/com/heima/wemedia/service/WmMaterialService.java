package com.heima.wemedia.service;

/**
 * @Author Administrator
 * @create 2021/2/2 8:40
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmMaterialDto;
import com.heima.model.media.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片业务接口
 */

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 查询素材列表
     * @param dto
     * @return
     */
    public ResponseResult queryList(WmMaterialDto dto);

    /**
     * 根据id删除素材图片
     * @param id
     * @return
     */
    public ResponseResult delPicture(Integer id);

    /**
     * 根据状态对素材做收藏,取消收藏操作
     * @param id
     * @param cancelCollectMaterial
     * @return
     */
    public ResponseResult updatePictureStatus(Integer id, Short cancelCollectMaterial);
}
