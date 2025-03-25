package com.hmall.api.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefautFeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

}
