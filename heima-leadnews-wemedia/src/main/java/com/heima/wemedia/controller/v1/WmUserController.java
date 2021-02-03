package com.heima.wemedia.controller.v1;

/**
 * @Author Administrator
 * @create 2021/1/30 21:55
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.api.wemedia.WmUserControllerApi;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.media.pojos.WmUser;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 自媒体-控制层
 */

@RestController
@RequestMapping("/api/v1/user")
public class WmUserController implements WmUserControllerApi {

    @Autowired
    private WmUserService wmUserService;

    /**
     * POST
     * 保存自媒体信息
     * @param wmUser
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody WmUser wmUser) {

        /*设置数据创建时间*/
        wmUser.setCreatedTime(new Date());

        wmUserService.save(wmUser);

        //int i = 1/0; /*测试分布式事务回滚*/

        /*保存成功--返回保存数据，因为文章用到了自媒体id*/
        return ResponseResult.okResult(wmUser);
    }

    /**
     * GET
     * 根据名称查询自媒体信息
     * @param name
     * @return
     */
    @GetMapping("/findByName/{name}")
    @Override
    public WmUser queryByName(@PathVariable("name") String name) {
        LambdaQueryWrapper<WmUser> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(WmUser::getName,name);
        List<WmUser> list = wmUserService.list(queryWrapper);
        /*不为空且有一条数据*/
        if (list!=null && list.size()==1){

            return list.get(0);
        }

        return null;
    }
}
