package com.heima.api.wemedia;

/**
 * @Author Administrator
 * @create 2021/2/2 8:35
 */

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmMaterialDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * 自媒体上传图片接口-controller
 */

@Api(value = "自媒体素材管理图片上传", tags = "leadnews-wemedia", description = "自媒体素材管理图片上传API")  //swagger 修饰整个类，描述Controller的作用
public interface WmMaterialControllerApi {

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    @ApiOperation("素材管理上传上传图片")
    public ResponseResult uploadPicture (MultipartFile multipartFile);

    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    @ApiOperation("素材列表查询")
    public ResponseResult queryList(WmMaterialDto dto);

    /**
     * 删除素材图片
     * @param id
     * @return
     */
    @ApiOperation("删除素材图片")
    public ResponseResult delPicture(Integer id);

    /**
     * 取消收藏图片
     * @param id
     * @return
     */
    @ApiOperation("取消收藏图片")
    public ResponseResult cancelCollectionMaterial(Integer id);

    /**
     * 收藏图片
     * @param id
     * @return
     */
    @ApiOperation("收藏图片")
    public ResponseResult collectionMaterial(Integer id);
}
