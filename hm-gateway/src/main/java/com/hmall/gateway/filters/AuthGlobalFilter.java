package com.hmall.gateway.filters;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.util.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    
    private final AuthProperties authProperties;
    
    private final JwtTool jwtTool;
    
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //登录校验
        // 1获取请求头
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        // 2判断是否需要校验
        if(isExcludePath(request.getPath().toString())){
            return chain.filter(exchange);
        }
        
        // 3校验token
        List<String> authorization = headers.get("authorization");
        String token = null;
        if(authorization != null && !authorization.isEmpty()){
            token = authorization.get(0);
        }
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            //拦截，设置响应码为401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        
        log.debug("用户ID：{}", userId);
        // 4传递用户信息
        String userInfo = userId.toString();
        ServerWebExchange exchange1 = exchange.mutate()
                .request(builder -> builder.header("user-info",userInfo))
                .build();
        
        return chain.filter(exchange1);
    }
    
    private boolean isExcludePath(String path) {
        for(String pathPatten:authProperties.getExcludePaths()){
            if(antPathMatcher.match(pathPatten,path)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
