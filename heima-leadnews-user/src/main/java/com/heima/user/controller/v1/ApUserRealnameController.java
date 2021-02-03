package com.heima.user.controller.v1;

/**
 * @Author Administrator
 * @create 2021/1/30 20:44
 */

import com.heima.api.user.ApUserRealnameControllerApi;
import com.heima.common.constants.user.UserConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.AuthDto;
import com.heima.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证-控制层
 */

@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealnameController implements ApUserRealnameControllerApi {

    @Autowired
    private ApUserRealnameService apUserRealnameService;

    /**
     * POST
     * 根据状态查询用户认证列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    @Override
    public ResponseResult loadListByStatus(@RequestBody AuthDto dto) {
        return apUserRealnameService.loadListByStatus(dto);
    }

    /**
     * POST
     * 审核通过
     * @param dto
     * @return
     */
    @PostMapping("/authPass")
    @Override
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return apUserRealnameService.updateStatusById(dto, UserConstants.PASS_AUTH);
    }

    /**
     * POST
     * 审核失败
     * @param dto
     * @return
     */
    @PostMapping("/authFail")
    @Override
    public ResponseResult authFail(@RequestBody AuthDto dto) {
        return apUserRealnameService.updateStatusById(dto, UserConstants.FAIL_AUTH);

    }
}
