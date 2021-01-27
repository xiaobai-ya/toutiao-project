package com.heima.model.admin.dtos;

/**
 * @Author Administrator
 * @create 2021/1/26 17:12
 */

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 频道-前端JSON封装
 */
@Data
public class ChannelDto extends PageRequestDto {


    /**
     * 频道名称-模糊查询
     */
    @ApiModelProperty("频道名称")    //swagger用对象接收参数时，描述对象的一个字段
    private String name;


}
