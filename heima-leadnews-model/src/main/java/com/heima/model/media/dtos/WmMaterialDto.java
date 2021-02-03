package com.heima.model.media.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 素材列表查询前端json封装
 */

@Data
public class WmMaterialDto extends PageRequestDto {
    /**
     * 1 查询收藏的
     */
    Short isCollection;
}