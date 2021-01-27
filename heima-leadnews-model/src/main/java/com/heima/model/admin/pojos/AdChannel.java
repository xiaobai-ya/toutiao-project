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
 * 频道信息表
 * </p>
 *
 * @author itheima
 */
@Data
@TableName("ad_channel")
public class AdChannel implements Serializable {


    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 频道名称
     */
    @ApiModelProperty("频道名称")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("name")
    private String name;

    /**
     * 频道描述
     */
    @ApiModelProperty("频道描述")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("description")
    private String description;

    /**
     * 是否默认频道
     */
    @ApiModelProperty("是否默认频道")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("is_default")
    private Boolean isDefault;

    /**
     * 频道是否有效
     */
    @ApiModelProperty("频道是否有效")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("status")
    private Boolean status;

    /**
     * 默认排序
     */
    @ApiModelProperty("默认排序")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("ord")
    private Integer ord;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("created_time")
    private Date createdTime;
}