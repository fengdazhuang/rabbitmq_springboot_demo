package com.fzz.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarningConsumer {
    private static final String WARNING_QUEUE="WarningQueue";

    @RabbitListener(queues = WARNING_QUEUE)
    public void receiveMsg(Message message){
        String msg = new String(message.getBody());
        log.info("WarningConsumer接受的消息为：{}",msg);
    }
}
