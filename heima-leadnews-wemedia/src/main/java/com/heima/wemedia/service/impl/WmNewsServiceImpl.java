package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.wemedia.WemediaContans;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.media.dtos.WmNewsDto;
import com.heima.model.media.dtos.WmNewsPageReqDto;
import com.heima.model.media.pojos.WmMaterial;
import com.heima.model.media.pojos.WmNews;
import com.heima.model.media.pojos.WmNewsMaterial;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import groovy.lang.DelegatesTo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.JodaTimeConverters;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @create 2021/2/2 9:39
 */

@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Value("${fdfs.url}")
    private String fastDFSHost;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;


    @Autowired
    private WmMaterialMapper wmMaterialMapper;


    /**
     * 查询文章列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult queryAll(WmNewsPageReqDto dto) {

        /*参数校验*/
        if (dto == null) {
            /*参数无效*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        /*分页参数校验*/
        dto.checkParam();

        /*构建分页条件*/
        Page<WmNews> page = new Page<>(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> queryWrapper = new LambdaQueryWrapper<>();

        /*获取要查询文章的状态*/
        if (dto.getStatus() != null) {
            queryWrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        /*时间条件,按文章的发布日期*/
        if (dto.getBeginPubdate() != null && dto.getEndPubdate() != null) {
            /*between ...之间*/
            queryWrapper.between(WmNews::getPublishTime, dto.getBeginPubdate(), dto.getEndPubdate());
        }

        /*指定频道*/
        if (dto.getChannelId() != null) {
            queryWrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        /*关键字按标题,模糊查询*/
        if (dto.getKeyword() != null) {
            queryWrapper.like(WmNews::getTitle, dto.getKeyword());
        }

        /*必要条件,只能查询自己的文章,文章列表按时间的倒序排序*/
        Integer uid = WmThreadLocalUtils.getUser().getId();
        queryWrapper.eq(WmNews::getUserId, uid);
        queryWrapper.orderByDesc(WmNews::getCreatedTime);

        IPage<WmNews> result = super.page(page, queryWrapper);

        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        /*数据填充*/
        pageResponseResult.setData(result.getRecords());
        pageResponseResult.setHost(fastDFSHost);//填充资源服务器获取地址fastDFS

        return pageResponseResult;
    }


    /**
     * 自媒体文章发布 --逻辑较为复杂
     *
     * @param dto
     * @param isSubmit 是否为提交 1 为提交 0为草稿
     * @return
     */
    @Override
    public ResponseResult saveNews(WmNewsDto dto, Short isSubmit) {

        /*参数校验*/
        if (dto == null || StringUtils.isBlank(dto.getContent())) {  //isBlank: 是在 isEmpty 的基础上进行了为空（字符串都为空格、制表符、tab 的情况）的判断

            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        /*保存文章*/
        WmNews wmNews = new WmNews();

        /*将前端实体类dto复制到wmNews*/
        BeanUtils.copyProperties(dto, wmNews);


        /*如果文章封面为自动,设置type为null  0 无图 1 单图 3 多图 -1 自动*/
        if (WemediaContans.WM_NEWS_AUTO_TYPE.equals(dto.getType())) {

            wmNews.setType(null);

        }

        /*如果是单图或者多图,对路径URI做截取操作,无图则为空,不做操作*/
        if ((WemediaContans.WM_NEWS_SINGLE_TYPE.equals(dto.getType()) || WemediaContans.WM_NEWS_MANY_TYPE.equals(dto.getType()) &&
                dto.getImages() != null && dto.getImages().size() > 0)) {

            dto.getImages().toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(fastDFSHost, "")
                    .replace("\"", "")
                    .replace(" ", "");
        }

        /*保存或者更新*/
        this.wmNewsSaveOrUpdate(wmNews);

        /*关联内容context图片与素材关系*/
        String content = dto.getContent();
        List<Map> contentMaps = JSONObject.parseArray(content, Map.class);
        /*获取路径集合*/
        List<String> urlList = this.ectractUrlInfo(contentMaps);

        /*需求-只有在提交的时候才保存文章与素材关系*/
        if (WmNews.Status.SUBMIT.getCode() == isSubmit && urlList.size() > 0) {

            ResponseResult responseResult = this.saveRelativeInfoForContent(urlList, wmNews.getId());

            if (responseResult != null) {

                return responseResult;
            }

        }

        /*需求-只有在提交的时候才会保存封面与素材的关系*/
        if (WmNews.Status.SUBMIT.getCode() == isSubmit) {
            ResponseResult responseResult = this.saveRelativeInfoForCover(dto, urlList, wmNews);

            if (responseResult != null) {
                return responseResult;
            }
        }

        /*发布成功*/
        return null;
    }


    /**
     * 为封面与素材建立关系
     *
     * @param dto
     * @param urlList
     * @param wmNews
     */
    private ResponseResult saveRelativeInfoForCover(WmNewsDto dto, List<String> urlList, WmNews wmNews) {

        List<String> collect = new ArrayList<>();

        /*封面为自动的方式逻辑处理*/
        if (WemediaContans.WM_NEWS_AUTO_TYPE.equals(dto.getType())) {

            /*三张图片*/
            if (urlList.size() >= 3) {
                /*设置封面类型多图显示*/
                wmNews.setType(WemediaContans.WM_NEWS_MANY_TYPE);

                collect = urlList.stream().limit(3).collect(Collectors.toList());

            }

            /*两张图片*/
            if (urlList.size() == 1 || urlList.size() == 2) {
                /*设置封面类型单图显示*/
                wmNews.setType(WemediaContans.WM_NEWS_SINGLE_TYPE);

                collect = urlList.stream().limit(1).collect(Collectors.toList());

            }

            /*没有图片*/
            if (urlList.size() == 0) {
                /*设置封面类型无图显示*/
                wmNews.setType(WemediaContans.WM_NEWS_NONE_TYPE);

                collect = urlList.stream().limit(1).collect(Collectors.toList());

            }

            /*如果有数据,对数据就行切割*/
            if (collect.size() > 0) {
                String realImage = collect.toString().replace("[", "").replace("]", "")
                        .replace(fastDFSHost, "").replace("\"", "").replace(" ", "");

                /*设置封面图片*/
                wmNews.setImages(realImage);
            }

            /*数据库更新数据*/
            super.updateById(wmNews);

            /*建立封面为自动模式关系*/
            return this.saveRelativeInfo(collect, wmNews.getId(), WemediaContans.WM_NEWS_COVER_REFERENCE);
        }

        /*建立封面 除自动模式的  单图, 多图  模式关系*/

        /*对images封面图片路径处理-去掉服务器地址*/  //TODO 这里修改了逻辑,如果报错先看这里
        List<String> imagesList = this.ectractUrlInfo(dto.getImages(), null);
        return this.saveRelativeInfo(imagesList, wmNews.getId(), WemediaContans.WM_NEWS_COVER_REFERENCE);

    }

    /**
     * 保存内容图片与素材关系
     *
     * @param urlList 图片集合
     * @param newsId  文章id
     *                WM_NEWS_CONTENT_REFERENCE : 引用类型 type   内容图片引用 0  封面图片引用1
     */
    private ResponseResult saveRelativeInfoForContent(List<String> urlList, Integer newsId) {
        return saveRelativeInfo(urlList, newsId, WemediaContans.WM_NEWS_CONTENT_REFERENCE);
    }

    /**
     * 操作文章与素材关系表  为内容图片与素材 或者 为封面图片与素材 建立关系
     *
     * @param urlList 图片路径,素材
     * @param newsId  文章id
     * @param type    引用类型  内容图片引用 0  封面图片引用1
     * @return
     */
    private ResponseResult saveRelativeInfo(List<String> urlList, Integer newsId, Short type) {

        /*获取素材id集合*/
        LambdaQueryWrapper<WmMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(WmMaterial::getUrl, urlList);
        queryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtils.getUser().getId());
        List<WmMaterial> wmMaterials = wmMaterialMapper.selectList(queryWrapper);

        /*获取到id集合*/
        List<Integer> materialsIdList = wmMaterials.stream().map(wmMaterial -> wmMaterial.getId()).collect(Collectors.toList());

        /*做批量存储操作*/
        wmNewsMaterialMapper.saveRelations(materialsIdList, newsId, type);
        return null;

    }

    /**
     * 获取内容中的图片地址,不需要fastDFS服务器地址
     *
     * @param contentMaps
     * @return
     */
    private List<String> ectractUrlInfo(List<Map> contentMaps) {
        ArrayList<String> urlList = new ArrayList<>();
        for (Map<String, String> contentMap : contentMaps) {

            if (WemediaContans.WM_NEWS_TYPE_IMAGE.equals(contentMap.get("type"))) {

                /*去服务器地址截取路径*/
                String value = contentMap.get("value");
                urlList.add(value.replace(fastDFSHost, ""));
            }

        }

        return urlList;
    }

    /**
     * --方法重载
     * 获取封面中的图片地址,不需要fastDFS服务器地址
     *
     * @param images 封面图片路径集合
     * @return
     */
    private List<String> ectractUrlInfo(List<String> images,Integer newsId) {
        ArrayList<String> imagesUrlList = new ArrayList<>();
        for (String image : images) {

            /*切割fastDFS服务器地址*/
            imagesUrlList.add(image.replace(fastDFSHost,""));
        }
        return imagesUrlList;
    }


    /**
     * 文章的保存或者更新方法
     *
     * @param wmNews
     */
    private void wmNewsSaveOrUpdate(WmNews wmNews) {

        /*补齐实体类中没有的参数*/
        wmNews.setUserId(WmThreadLocalUtils.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setPublishTime(new Date());
        wmNews.setEnable((short) 1);  //TODO 上架/下架状态

        if (wmNews.getId() == null) {
            /*保存*/
            super.save(wmNews);
        } else {
            /*更新逻辑-删除旧的文章与素材关联数据*/
            LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WmNewsMaterial::getNewsId, wmNews.getId());
            wmNewsMaterialMapper.delete(queryWrapper);

            /*更新数据*/
            super.updateById(wmNews);

        }
    }

    /**
     * 根据id查询文章 -回显
     *
     * @param id 文章id
     * @return
     */
    @Override
    public ResponseResult queryWmNewsById(Integer id) {
        /**
         * 参数校验
         * 逻辑处理
         * 返回结果
         */

        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews wmNews = super.getById(id);

        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }


        ResponseResult responseResult = ResponseResult.okResult(wmNews);
        responseResult.setHost(fastDFSHost); //FastDfs服务器地址
        return responseResult;
    }

    /**
     * 根据id删除文章
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult delNews(Integer id) {
        /**
         * 参数校验
         * 逻辑处理
         * 返回结果
         */

        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews wmNews = super.getById(id);

        if (wmNews == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }


        /*如果状态为9-已发布  enable为1 -已上架  不能删除*/
        if (WmNews.Status.PUBLISHED.getCode() == wmNews.getStatus() && WemediaContans.WM_NEWS_ENABLE_UP == wmNews.getEnable()) {

            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "文章已经发布,删除失败!");
        }

        /*未发布,未上架 -删除文章与素材关系*/
        LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmNewsMaterial::getNewsId, wmNews.getId());
        wmNewsMaterialMapper.delete(queryWrapper);

        /*删除文章数据*/
        super.removeById(wmNews.getId());

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 文章的伤下架操作
     * 只有状态未9-已发布 的文章才可以上下架操作
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
        /**
         * 参数校验
         * 逻辑处理
         * 返回结果
         */

        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews wmNews = super.getById(dto.getId());

        if (wmNews == null) {
            /*数据不存在*/
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        if (WmNews.Status.PUBLISHED.getCode() != wmNews.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "该文章还未发布,不能上架!");
        }

        /*修改文章上架状态*/ //同步到app端（后期做）TODO
        if (dto.getEnable() != null && dto.getEnable() > -1 && dto.getEnable() < 2) {

            LambdaUpdateWrapper<WmNews> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(WmNews::getEnable,dto.getEnable()).eq(WmNews::getId,dto.getId());

            super.update(updateWrapper);

        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
