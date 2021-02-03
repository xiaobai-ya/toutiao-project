package com.heima.wemedia.mapper;

/**
 * @Author Administrator
 * @create 2021/2/2 13:55
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.media.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章和素材关联表-数据操作层
 */

@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {

    /**
     * 批量添加数据，用于素材与文章关系做关联
     * @param materials 素材id集合
     * @param newsId 文章id
     * @param type 引用类型 内容图片引用 0  封面图片引用1
     */
    public void saveRelations(@Param("materials") List<Integer> materials, @Param("newsId") Integer newsId, @Param("type") int type);
}
