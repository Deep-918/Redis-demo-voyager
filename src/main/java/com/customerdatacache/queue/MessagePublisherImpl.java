package com.customerdatacache.queue;

import com.customerdatacache.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisherImpl implements MessagePublisher {
    
    @Autowired
    private RedisTemplate<String, Customer> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    public MessagePublisherImpl() {
        System.out.println("Entering MesssagePublisher's default constructor!");
    }

    public MessagePublisherImpl(final RedisTemplate<String, Customer> redisTemplate, final ChannelTopic topic) {
        System.out.println("Entering MesssagePublisher's argumented constructor!");
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    public void publish(final String message) {
        System.out.println("Publishing message now.");
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
