package com.heima.model.admin.dtos;

/**
 * @Author Administrator
 * @create 2021/1/27 22:54
 */

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 敏感词汇-前端json封装
 */

@Data
public class SensitiveDto extends PageRequestDto {

    /**
     * 敏感词名称-模糊查询
     */
    @ApiModelProperty("敏感词名称")    //swagger用对象接收参数时，描述对象的一个字段
    private String name;
}
