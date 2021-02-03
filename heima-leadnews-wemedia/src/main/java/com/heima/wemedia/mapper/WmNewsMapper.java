package com.heima.wemedia.mapper;

/**
 * @Author Administrator
 * @create 2021/2/2 9:36
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.media.pojos.WmNews;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自媒体文章-数据操作层
 */

@Mapper
public interface WmNewsMapper extends BaseMapper<WmNews> {
}
