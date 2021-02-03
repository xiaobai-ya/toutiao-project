package com.heima.model.article.pojos;

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
 * APP文章作者信息表
 * </p>
 *
 * @author itheima
 */
@Data
@TableName("ap_author")
public class ApAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键 id")    //swagger用对象接收参数时，描述对象的一个字段
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作者名称
     */
    @ApiModelProperty("作者名称")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("name")
    private String name;

    /**
     * 0 爬取数据
            1 签约合作商
            2 平台自媒体人
            
     */
    @ApiModelProperty("0 爬取数据  1 签约合作商  2 平台自媒体人")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("type")
    private Integer type;

    /**
     * 社交账号ID
     */
    @ApiModelProperty("社交账号ID")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("created_time")
    private Date createdTime;

    /**
     * 自媒体账号
     */
    @ApiModelProperty("自媒体账号")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("wm_user_id")
    private Integer wmUserId;

}