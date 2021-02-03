package com.heima.wemedia.controller.v1;

/**
 * @Author Administrator
 * @create 2021/2/2 0:19
 */

import com.heima.api.wemedia.LoginControllerApi;
import com.heima.api.wemedia.WmUserControllerApi;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.media.dtos.WmUserDto;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体登录-控制层
 */

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private WmUserService wmUserService;

    /**
     * POST
     * 自媒体登录校验
     * @param dto
     * @return
     */
    @PostMapping("/in")
    @Override
    public ResponseResult login(@RequestBody WmUserDto dto){
        return wmUserService.login(dto);
    }
}
