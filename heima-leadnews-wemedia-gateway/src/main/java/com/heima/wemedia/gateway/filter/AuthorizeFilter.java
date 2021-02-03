package com.heima.wemedia.gateway.filter;

import com.heima.wemedia.gateway.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @Author Administrator
 * @create 2021/1/30 10:48
 */

/**
 * 网关全局过滤器
 */
@Component
@Log4j2
public class AuthorizeFilter implements GlobalFilter , Ordered {

    /**
     * 过滤逻辑
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        /*获取访问路径*/
        URI uri = request.getURI();
        String path = uri.getPath();
        log.info("Filter-URI:{}",uri);
        log.info("Filter-path:{}",path);

        /*放行登录请求*/
        if (path.contains("/login/in")){

            /*放行*/
            return chain.filter(exchange);
        }


        /*已经登录，访问资源,获取jwt*/
        HttpHeaders headers = request.getHeaders();
        String jwtToken = headers.getFirst("token");
        if (StringUtils.isEmpty(jwtToken)){

            /*未授权*/
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        /*解析令牌  -1：有效，0：有效，1：过期，2：过期*/
        Claims claimsBody = AppJwtUtil.getClaimsBody(jwtToken);
        log.info("claimsBody:",claimsBody);
        int time = AppJwtUtil.verifyToken(claimsBody);
        if (time==1 || time ==2){

            /*认证过期*/
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        /*放行,对header添加用户id的属性,后期过滤权限角色,   固定写法！！！*/
        Integer uid = (Integer) claimsBody.get("id");
        ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> httpHeaders.add("userId", String.valueOf(uid))).build();

        exchange.mutate().request(serverHttpRequest).build();

        /*放行*/
        return chain.filter(exchange);

    }


    /**
     * 过滤器执行优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
