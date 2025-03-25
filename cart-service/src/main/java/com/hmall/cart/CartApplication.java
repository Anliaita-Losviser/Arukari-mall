package com.hmall.cart;

import com.hmall.api.config.DefautFeignConfig;
import com.hmall.common.constant.ApiPackgeNameConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.hmall.cart.mapper")
@SpringBootApplication
@EnableFeignClients(basePackages = ApiPackgeNameConstant.API_PACKAGE_NAME_CLIENT,
        defaultConfiguration = DefautFeignConfig.class)
@EnableDiscoveryClient
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
