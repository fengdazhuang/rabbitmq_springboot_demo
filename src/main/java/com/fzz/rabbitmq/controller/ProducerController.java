package com.fzz.rabbitmq.controller;

import com.fzz.rabbitmq.config.MyCallbackConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
@RequestMapping("/confirm")
public class ProducerController {

    private static final String CONFIRM_EXCHANGE="ConfirmExchange";
    private static final String CONFIRM_ROUTING_KEY="key1";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyCallbackConfig myCallbackConfig;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(myCallbackConfig);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(myCallbackConfig);
    }
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        CorrelationData correlationData1=new CorrelationData("1");
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE,CONFIRM_ROUTING_KEY+"33",message,correlationData1);
        log.info("发送的消息为：{}",message);
    }

}
