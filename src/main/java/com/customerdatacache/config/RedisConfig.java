package com.customerdatacache.config;

import com.customerdatacache.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.customerdatacache.queue.MessagePublisher;
import com.customerdatacache.queue.MessagePublisherImpl;
import com.customerdatacache.queue.MessageSubscriber;

@Configuration
@ComponentScan("com.customerdatacache")
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        System.out.println("Inside Configuration");
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Customer> redisTemplate() {
        System.out.println("Inside Configuration: Initializing redisTemplate");
        final RedisTemplate<String, Customer> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        //template.setValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        System.out.println("Listening...");
        return new MessageListenerAdapter(new MessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        System.out.println("Publishing...");
        return new MessagePublisherImpl(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
}