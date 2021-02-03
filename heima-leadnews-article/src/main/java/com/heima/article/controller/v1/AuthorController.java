package com.heima.article.controller.v1;

/**
 * @Author Administrator
 * @create 2021/1/30 22:43
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heima.api.article.AuthorControllerApi;
import com.heima.article.service.AuthorService;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 文章.作者-控制层
 */

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController implements AuthorControllerApi {

    @Autowired
    private AuthorService authorService;

    /**
     * POST
     * 保存作者信息
     *
     * @param apAuthor
     * @return
     */
    @PostMapping("/save")
    @Override
    public ResponseResult save(@RequestBody ApAuthor apAuthor) {

        /*填充数据创建时间*/
        apAuthor.setCreatedTime(new Date());

        //int i = 1/0;  //测试分布式回滚

        authorService.save(apAuthor);


        /*保存成功*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * GET
     * 根据用户id查询作者信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findByUserId/{id}")
    @Override
    public ApAuthor queryByUserId(@PathVariable("id") Integer id) {

        LambdaQueryWrapper<ApAuthor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApAuthor::getUserId, id);

        List<ApAuthor> list = authorService.list(queryWrapper);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }
}
