package com.heima.admin.controller.v1;

import com.heima.admin.service.UserLoginService;
import com.heima.api.admin.LoginControllerApi;
import com.heima.model.admin.dtos.AdUserDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Administrator
 * @create 2021/1/29 22:52
 */
@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private UserLoginService userLoginService;


    /**
     * POST
     * 管理员登录校验
     * @param dto
     * @return
     */
    @PostMapping("/in")
    @Override
    public ResponseResult login(@RequestBody AdUserDto dto){
        return userLoginService.login(dto);
    }
}
