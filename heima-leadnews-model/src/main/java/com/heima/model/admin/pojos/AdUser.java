package com.heima.model.admin.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理员用户信息表
 * </p>
 *
 * @author itheima
 */
@Data
@TableName("ad_user")
public class AdUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("id")    //swagger用对象接收参数时，描述对象的一个字段
    private Integer id;

    /**
     * 登录用户名
     */
    @ApiModelProperty("登录用户名")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("name")
    private String name;

    /**
     * 登录密码
     */
    @ApiModelProperty("登录密码")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("password")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty("盐")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("salt")
    private String salt;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty("头像")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("image")
    private String image;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("phone")
    private String phone;

    /**
     * 状态
            0 暂时不可用
            1 永久不可用
            9 正常可用
     */
    @ApiModelProperty("状态")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("status")
    private Integer status;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("email")
    private String email;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty("最后一次登录时间")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("login_time")
    private Date loginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")    //swagger用对象接收参数时，描述对象的一个字段
    @TableField("created_time")
    private Date createdTime;

}