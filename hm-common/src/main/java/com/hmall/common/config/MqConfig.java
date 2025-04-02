package com.hmall.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqConfig {
    
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
//    @PostConstruct
//    public void init(){
//        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
//            @Override
//            public void returnedMessage(ReturnedMessage returned) {
//                log.error("监听到消息return callback");
//                log.error("交换机:{}, 路由键:{}, 消息:{}",returned.getExchange(),returned.getRoutingKey(),returned.getMessage());
//                log.error("原因:{}, {}",returned.getReplyCode(),returned.getReplyText());
//            }
//        });
//
//    }
    
    @Bean
    public RabbitTemplate.ReturnsCallback returnsCallback(){
        return new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.error("监听到消息return callback");
                log.error("交换机:{}, 路由键:{}, 消息:{}",returned.getExchange(),returned.getRoutingKey(),returned.getMessage());
                log.error("原因:{}, {}",returned.getReplyCode(),returned.getReplyText());
            }
        };
    }
}
