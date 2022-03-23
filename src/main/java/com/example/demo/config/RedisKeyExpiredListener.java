package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

/**
 * @program: limiting-demo
 * @description: 监听rediskey过期监听器
 * @author: Jian Chen
 * @create: 2021-12
 **/
@Slf4j
@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private static final Topic KEYEVENT_EXPIRED_TOPIC = new PatternTopic("__keyevent@1__:expired");

    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        log.info("----------------------初始化RedisKeyExpiredListener");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        if (key.startsWith("CJ")){
            log.info("key:{},过期了-------------",key);
        }
    }
    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, KEYEVENT_EXPIRED_TOPIC);
    }
}
