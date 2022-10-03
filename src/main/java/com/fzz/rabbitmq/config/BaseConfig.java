package com.fzz.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {
    private static final String CONFIRM_EXCHANGE="ConfirmExchange";
    private static final String CONFIRM_QUEUE="ConfirmQueue";
    private static final String CONFIRM_ROUTING_KEY="key1";
    private static final String BACKUP_EXCHANGE="BackupExchange";
    private static final String BACKUP_QUEUE="BackupQueue";
    private static final String WARNING_QUEUE="WarningQueue";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).durable(true)
                        .withArgument("alternate-exchange",BACKUP_EXCHANGE).build();
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean("key1")
    public Binding bind1(
            @Qualifier("confirmExchange") DirectExchange confirmExchange,
            @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }
    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }
    @Bean
    public Binding bind2(
            @Qualifier("backupExchange") FanoutExchange backupExchange,
            @Qualifier("backupQueue") Queue backupQueue){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
    @Bean
    public Binding bind3(
            @Qualifier("backupExchange") FanoutExchange backupExchange,
            @Qualifier("warningQueue") Queue warningQueue){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }


}
