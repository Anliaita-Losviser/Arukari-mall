package com.hmall.pay;

import com.hmall.api.config.DefautFeignConfig;
import com.hmall.common.constant.ApiPackgeNameConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.hmall.pay.mapper")
@EnableFeignClients(basePackages = ApiPackgeNameConstant.API_PACKAGE_NAME_CLIENT,
        defaultConfiguration = DefautFeignConfig.class)
@SpringBootApplication
@EnableDiscoveryClient
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
