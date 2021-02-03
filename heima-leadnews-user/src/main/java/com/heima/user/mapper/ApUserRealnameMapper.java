package com.heima.user.mapper;

/**
 * @Author Administrator
 * @create 2021/1/30 20:26
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.user.pojos.ApUserRealname;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户认证-数据操作层
 */

@Mapper
public interface ApUserRealnameMapper extends BaseMapper<ApUserRealname> {
}
