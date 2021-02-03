package com.heima.user.mapper;

/**
 * @Author Administrator
 * @create 2021/1/30 23:12
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.user.pojos.ApUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * App用户-数据操作层
 */

@Mapper
public interface ApUserMapper extends BaseMapper<ApUser> {
}
