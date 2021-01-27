package com.heima.model.common.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用分页请求封装
 */

@Data
@Slf4j
public class PageRequestDto {

    /**
     * 条
     */
    @ApiModelProperty(value="每页显示条数",required = true)  //swagger用对象接收参数时，描述对象的一个字段
    protected Integer size;
    /**
     * 页
     */
    @ApiModelProperty(value="当前页",required = true)  //swagger用对象接收参数时，描述对象的一个字段
    protected Integer page;

    /**
     * 校验 分页参数,如果为空,设定默认值
     */
    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
