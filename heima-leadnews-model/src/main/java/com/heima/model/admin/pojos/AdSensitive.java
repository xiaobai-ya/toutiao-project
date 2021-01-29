package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 敏感词信息表
 * </p>
 *
 * @author itheima
 */
@Data
@TableName("ad_sensitive")
public class AdSensitive implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("id")    //swagger用对象接收参数时，描述对象的一个字段
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 敏感词
     */
    @ApiModelProperty("敏感词")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("sensitives")
    private String sensitives;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("created_time")
    private Date createdTime;

}