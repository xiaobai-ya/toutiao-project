package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.media.pojos.WmUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Administrator
 * @create 2021/1/30 21:51
 */


/**
 * 自媒体-数据操作层
 */

@Mapper
public interface WmUserMapper extends BaseMapper<WmUser> {
}
