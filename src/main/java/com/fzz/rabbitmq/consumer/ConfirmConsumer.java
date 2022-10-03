package com.fzz.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ConfirmConsumer {
    private static final String CONFIRM_QUEUE = "ConfirmQueue";

    @RabbitListener(queues = CONFIRM_QUEUE)
    public void receiveMsg(Message message){
        String msg = new String(message.getBody());
        log.info("confirmConsumer接受的消息为：{}",msg);
    }

}
