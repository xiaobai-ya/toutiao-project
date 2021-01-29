package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.UserLoginService;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import com.heima.utils.common.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Administrator
 * @create 2021/1/29 21:59
 */
@Service
public class UserLoginServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements UserLoginService {

    /**
     * 管理员用登录校验
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(AdUserDto dto) {

        /*校验参数*/
        if (dto==null){

            /*参数无效*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (StringUtils.isEmpty(dto.getName())||StringUtils.isEmpty(dto.getPassword())){

            return ResponseResult.errorResult(500,"用户名或者密码不能为空！");
        }

        /*校验用户信息*/
        LambdaQueryWrapper<AdUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdUser::getName,dto.getName());

        List<AdUser> list = super.list(queryWrapper);

        /*如果用户为空，或者查询回来不止一条，登录失败*/
        if (CollectionUtils.isEmpty(list)||list.size()!=1){

            /*用户不存在,登录失败*/
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_FAIL);

        }

        /*密码验证*/
        AdUser adUser = list.get(0);
        String pwd = MD5Utils.encodeWithSalt(dto.getPassword(), adUser.getSalt());
        if (!pwd.equals(adUser.getPassword())){

            /*密码错误*/
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);

        }

        /*生成jwt token*/

        HashMap params = new HashMap<>();
        params.put("token", AppJwtUtil.getToken(Long.parseLong(adUser.getId()+"")));

        /*密码清空，密钥清空（盐）*/
        adUser.setSalt("");
        adUser.setPassword("");

        params.put("user",adUser);

        /*数据返回*/
        return ResponseResult.okResult(params);
    }
}
