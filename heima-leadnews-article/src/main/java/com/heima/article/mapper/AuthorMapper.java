package com.heima.article.mapper;

/**
 * @Author Administrator
 * @create 2021/1/30 22:38
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.pojos.ApAuthor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章.作者-数据操作层
 */

@Mapper
public interface AuthorMapper extends BaseMapper<ApAuthor> {
}
