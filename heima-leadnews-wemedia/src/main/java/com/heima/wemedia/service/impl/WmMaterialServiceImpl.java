package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.fastdfs.FastDFSClient;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.media.dtos.WmMaterialDto;
import com.heima.model.media.pojos.WmMaterial;
import com.heima.model.media.pojos.WmNewsMaterial;
import com.heima.model.media.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @create 2021/2/2 8:41
 */

@Service
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    /*fastDFS客户端*/
    @Autowired
    private FastDFSClient fastDFSClient;

    /*fastDFS服务器路径*/
    @Value("${fdfs.url}")
    private String fileServerURL;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {

        /*数据校验*/
        if (multipartFile == null) {

            /*无效参数*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        /*上传图片*/
        try {
            String path = fastDFSClient.uploadFile(multipartFile);

            /*数据保存到数据库*/
            WmMaterial wmMaterial = new WmMaterial();

            /*获取线程用户id*/
            Integer uid = WmThreadLocalUtils.getUser().getId();

            wmMaterial.setUserId(uid);
            wmMaterial.setUrl(path);
            wmMaterial.setIsCollection((short) 0);  //TODO 修改常量类
            wmMaterial.setType((short) 0);  //TODO 修改常量类
            wmMaterial.setCreatedTime(new Date());

            super.save(wmMaterial);

            /*拼接图片从fastDFS正确的访问地址-返回前端使用*/
            wmMaterial.setUrl(fileServerURL + path);

            return ResponseResult.okResult(wmMaterial);


        } catch (IOException e) {
            e.printStackTrace();
            /*上传异常*/
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    /**
     * 查询素材列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult queryList(WmMaterialDto dto) {
        /**
         * 参数校验
         * 执行逻辑
         * 数据封装返回
         */

        if (dto == null) {

            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        dto.checkParam();

        Page<WmMaterial> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmMaterial> queryWrapper = new LambdaQueryWrapper<>();

        /*只查询当前用户的数据*/
        Integer uid = WmThreadLocalUtils.getUser().getId();
        queryWrapper.eq(WmMaterial::getUserId, uid);

        if (dto.getIsCollection() != null && dto.getIsCollection() != (short) 0) {

            queryWrapper.eq(WmMaterial::getIsCollection, dto.getIsCollection());
        }

        IPage<WmMaterial> result = super.page(page, queryWrapper);

        /*对数据做处理*/
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        List<WmMaterial> records = result.getRecords();
        /*为每一个素材拼接fastDFS服务器路径*/ // TODO stream()
        List<WmMaterial> collect = records.stream().map(material -> {
            material.setUrl(fileServerURL + material.getUrl());
            return material;
        }).collect(Collectors.toList());

        /*数据填充*/
        pageResponseResult.setData(collect);

        return pageResponseResult;
    }

    /**
     * 根据id删除素材图片
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult delPicture(Integer id) {
        /**
         * 参数校验
         * 执行逻辑
         * 数据封装返回
         */
        if (id == null) {

            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        /*检查该素材是否被引用*/
        LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmNewsMaterial::getMaterialId, id);
        Integer count = wmNewsMaterialMapper.selectCount(queryWrapper);
        if (count > 0) {

            return ResponseResult.errorResult(500, "该素材被引用,删除失败!");

        }

        /*删除fastDFS中的图片,不需要服务器地址*/
        String path = super.getById(id).getUrl().replace(fileServerURL, "");
        fastDFSClient.delFile(path);

        /*删除素材表数据*/
        super.removeById(id);


        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 根据状态对素材做收藏,取消收藏操作
     *
     * @param id
     * @param type 操作类型,取消  收藏
     * @return
     */
    @Override
    public ResponseResult updatePictureStatus(Integer id, Short type) {

        if (id == null || type == null){

            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        /*只能对自己的素材做收藏和取消收藏操作*/
        Integer uid = WmThreadLocalUtils.getUser().getId();

        LambdaUpdateWrapper<WmMaterial> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(WmMaterial::getIsCollection,type).eq(WmMaterial::getId,id).eq(WmMaterial::getUserId,uid);

        super.update(updateWrapper);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }
}
