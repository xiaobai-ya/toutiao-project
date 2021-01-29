package com.heima.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.admin.pojos.AdSensitive;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Administrator
 * @create 2021/1/27 23:09
 */

/**
 * 敏感词-数据操作层
 */

@Mapper
public interface AdSensitiveMapper extends BaseMapper<AdSensitive> {
}
