package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.user.UserConstants;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.media.pojos.WmUser;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.user.feign.ArticleFeign;
import com.heima.user.feign.WemediaFeign;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.mapper.ApUserRealnameMapper;
import com.heima.user.service.ApUserRealnameService;
import jdk.nashorn.internal.ir.ReturnNode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

/**
 * @Author Administrator
 * @create 2021/1/30 20:30
 */
@Service
@Log4j2
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {


    /*远程调用文章服务*/
    @Autowired
    private ArticleFeign articleFeign;

    /*远程调用自媒体服务*/
    @Autowired
    private WemediaFeign wemediaFeign;
    
    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 根据状态-查询用户认证分页列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {

        /*参数校验*/
        if (dto==null){

            /*参数错误*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        /*分页参数校验*/
        dto.checkParam();

        /*构建查询条件*/
        Page<ApUserRealname> page = new Page<>(dto.getPage(),dto.getSize());
        LambdaQueryWrapper<ApUserRealname> queryWrapper = new LambdaQueryWrapper<>();

        if (dto.getStatus()!=null){

            /*按照状态查询,--如果无状态就是查询全部*/
            queryWrapper.eq(ApUserRealname::getStatus,dto.getStatus());
        }

        IPage<ApUserRealname> result = super.page(page, queryWrapper);

        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(), dto.getSize(), (int) result.getTotal());
        /*数据装填*/
        pageResponseResult.setData(result.getRecords());


        return pageResponseResult;
    }


    /**
     * 实名认证的通过,或者驳回
     * 根据状态进行审核.更新状态
     * @param dto
     * @param status
     * @return
     */
    //@Transactional  /*该注解只能保证本地事务,也就是当前微服务,保证不了远程掉用的微服务,这里远程调用了自媒体和文章的微服务*/ //--seata接管本地事务失效,配合本地事务使用
    //@GlobalTransactional  /*seata分布式事务控制,解决远程调用事务*/
    @Override
    public ResponseResult updateStatusById(AuthDto dto, Short status) {

        /*参数校验*/
        if (dto==null || dto.getId()==null){

            /*参数错误*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        /*状态校验*/
        if (!this.checkStatus(status)){

            /*参数错误*/
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        }

        /*判断当前用户是否已经有审核记录*/
        Integer userRealnameId = dto.getId();
        ApUserRealname one = super.getById(userRealnameId);

        if (one==null){

            return ResponseResult.errorResult(500,"审核失败,没有改用户的认证请求记录!");

        }

        /*如果有认证记录,更新状态 ,并更新反驳信息*/
        one.setStatus(status);
        if  (StringUtils.isNotEmpty(dto.getMsg())){
            one.setReason(dto.getMsg());
        }

        super.updateById(one);


        /*如果是通过的状态,为该用户开通文章和自媒体*/
        if (UserConstants.PASS_AUTH.equals(status)){

            /*执行开通逻辑*/
            ResponseResult result = this.createWmUserAndAuthor(dto);

            if (result!=null){

                return result;
            }


        }


        /*审核拒绝--逻辑没有创建文章和自媒体的服务*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }


    /**
     * 为当前审核成功的用户,开通自媒体和文章服务
     * @param dto
     * @return
     */
    private ResponseResult createWmUserAndAuthor(AuthDto dto) {

        /*获取认证用户详细信息*/
        Integer userRealnameId = dto.getId();
        ApUserRealname userRealname = super.getById(userRealnameId);
        Integer userId = userRealname.getUserId();

        /*查询详细信息*/
        ApUser apUser = apUserMapper.selectById(userId);


        /*判断用户,自媒体服务是否存在*/
        WmUser wmUser = wemediaFeign.queryByName(apUser.getName());

         if (wmUser==null){
            /*用户不存在*/
            wmUser = new WmUser();

            wmUser.setApUserId(apUser.getId());
            wmUser.setCreatedTime(new Date());
            wmUser.setName(apUser.getName());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setPhone(apUser.getPhone());
            wmUser.setStatus(9);

            /*保存*/
            ResponseResult saveResponse = wemediaFeign.save(wmUser);

             try {
                 /*为wmUser对象重新赋值,获得保存后的自增id给文章服务使用,因为使用了@restcontroller所以返回的是json,这里需要转换一下
                 * 这个API也可以使用 JsonNode jsonNode = OBJECT_MAPPER.readTree(OBJECT_MAPPER.writeValueAsString(data));
                 * 为什么使用 OBJECT_MAPPER.writeValueAsString(data): 数据对比:
                 * String.valueOf(data); : Wemedia虽然返回的是json但是还是以对象的方式展现，使用这个Jackson会报错
                 * {id=1139, apUserId=5, apAuthorId=null, name=ceshishuju, password=5d4e1a406d4a9edbf7b4f10c2a390405, salt=123abc, nickname=null, image=null, location=null, phone=13511223456, status=9, email=null, type=null, score=null, loginTime=null, createdTime=2021-02-01T12:53:34.337+0000}
                 *
                 * System.out.println(OBJECT_MAPPER.writeValueAsString(data)); :
                 * {"id":1139,"apUserId":5,"apAuthorId":null,"name":"ceshishuju","password":"5d4e1a406d4a9edbf7b4f10c2a390405","salt":"123abc","nickname":null,"image":null,"location":null,"phone":"13511223456","status":9,"email":null,"type":null,"score":null,"loginTime":null,"createdTime":"2021-02-01T12:53:34.337+0000"}
                 * */

                 Object data = saveResponse.getData();
                 /*获取标准的json字符串,不是以对象的方式(Object)*/
                 String dataJson = OBJECT_MAPPER.writeValueAsString(data);

                 wmUser = OBJECT_MAPPER.readValue(dataJson,WmUser.class);


             } catch (IOException e) {
                 e.printStackTrace();
                 return ResponseResult.errorResult(500,"服务器繁忙,请稍后再试...");
             }


         }
        //TODO!! 如果数据已经存在呢? 对自媒体做修改！！




        /*创建文章服务*/
        this.createAuthor(wmUser);

        /*将用户状态变更为自媒体人  0 普通用户  1 自媒体人  2 大V*/
        apUser.setFlag((short)1);

        /*更新数据*/
        apUserMapper.updateById(apUser);

        /*创建成功*/
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 为当前审核成功的用户,开通文章服务
     * @param wmUser
     */
    private void createAuthor(WmUser wmUser) {
        /*判断用户,文章服务是否存在*/
        ApAuthor apAuthor = articleFeign.queryByUserId(wmUser.getApUserId());
        if (apAuthor==null){

            apAuthor=new ApAuthor();
            apAuthor.setName(wmUser.getName());
            apAuthor.setType(2);
            apAuthor.setUserId(wmUser.getApUserId());
            apAuthor.setCreatedTime(new Date());
            apAuthor.setWmUserId(wmUser.getId());
            //2保存
            articleFeign.save(apAuthor);
        }

    }


    /**
     * 检查状态信息 2审核失败  9审核通过 只能是这两种状态
     * @param status
     * @return
     */
    private boolean checkStatus(Short status) {

        if (UserConstants.PASS_AUTH.equals(status) || UserConstants.FAIL_AUTH.equals(status)){

            /*参数合法*/
            return true;
        }

        /*参数不合法*/
        return false;
    }
}
