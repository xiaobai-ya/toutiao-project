package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户认证-前端json封装
 *
 * 说明:前端只有在查询认证列表的时候会传递"状态"值
 *     在审核通过,或者是驳回的时候前端只向后端传输了认证的id值
 */

@Data
public class AuthDto extends PageRequestDto {

    /**
     * 认证表唯一标识id
     */
    @ApiModelProperty("认证表唯一标识id")    //swagger用对象接收参数时，描述对象的一个字段
    private Integer id;

    /**
     * 驳回信息
     */
    @ApiModelProperty("驳回信息")    //swagger用对象接收参数时，描述对象的一个字段
    private String msg;

    /**
     * 状态
     */
    @ApiModelProperty("状态")    //swagger用对象接收参数时，描述对象的一个字段
    private Short status;
}