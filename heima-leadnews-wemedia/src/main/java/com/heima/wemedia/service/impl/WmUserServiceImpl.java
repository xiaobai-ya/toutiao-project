package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.media.dtos.WmUserDto;
import com.heima.model.media.pojos.WmUser;
import com.heima.utils.common.AppJwtUtil;
import com.heima.utils.common.MD5Utils;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Administrator
 * @create 2021/1/30 21:54
 */
@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    /**
     * 自媒体登录-登录校验
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(WmUserDto dto) {

        /*校验参数*/
        if (dto==null){

            /*参数无效*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        if (StringUtils.isEmpty(dto.getName())||StringUtils.isEmpty(dto.getPassword())){

            return ResponseResult.errorResult(500,"用户名或者密码不能为空！");
        }

        /*校验用户信息*/
        LambdaQueryWrapper<WmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmUser::getName,dto.getName());

        List<WmUser> list = super.list(queryWrapper);

        /*如果用户为空，或者查询回来不止一条，登录失败*/
        if (CollectionUtils.isEmpty(list)||list.size()!=1){

            /*用户不存在,登录失败*/
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_FAIL);

        }

        /*密码验证*/
        WmUser wmUser = list.get(0);
        String pwd = MD5Utils.encodeWithSalt(dto.getPassword(), wmUser.getSalt());
        if (!pwd.equals(wmUser.getPassword())){

            /*密码错误*/
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);

        }

        /*生成jwt token*/

        HashMap params = new HashMap<>();
        params.put("token", AppJwtUtil.getToken(Long.parseLong(wmUser.getId()+"")));

        /*密码清空，密钥清空（盐）*/
        wmUser.setSalt("");
        wmUser.setPassword("");

        params.put("user",wmUser);

        /*数据返回*/
        return ResponseResult.okResult(params);
    }
}
