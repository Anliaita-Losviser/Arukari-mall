package com.hmall.config;

import cn.hutool.core.collection.CollUtil;
import com.hmall.interceptor.LoginInterceptor;
import com.hmall.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class MvcConfig implements WebMvcConfigurer {

   private final JwtTool jwtTool;
   private final AuthProperties authProperties;

/*    @Bean
    public CommonExceptionAdvice commonExceptionAdvice(){
        return new CommonExceptionAdvice();
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1.添加拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor(jwtTool);
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
        // 2.配置拦截路径
        List<String> includePaths = authProperties.getIncludePaths();
        if (CollUtil.isNotEmpty(includePaths)) {
            registration.addPathPatterns(includePaths);
        }
        // 3.配置放行路径
        List<String> excludePaths = authProperties.getExcludePaths();
        if (CollUtil.isNotEmpty(excludePaths)) {
            registration.excludePathPatterns(excludePaths);
        }
        registration.excludePathPatterns(
                "/error",
                "/favicon.ico",
                "/v2/**",
                "/v3/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/doc.html"
                );

    }
    
    /**
     * 跨域拦截放行
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域路径
        CorsRegistration cors = registry.addMapping("/**");
        // 可访问的外部域
        cors.allowedOrigins("*");
        // 支持跨域用户凭证
        //cors.allowCredentials(true);
        //cors.allowedOriginPatterns("*");
        // 设置 header 能携带的信息
        cors.allowedHeaders("*");
        // 支持跨域的请求方法
        cors.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        // 设置跨域过期时间，单位为秒
        cors.maxAge(3600);
    }
}
