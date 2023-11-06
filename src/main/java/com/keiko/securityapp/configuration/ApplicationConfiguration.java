package com.keiko.securityapp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper () {
        ModelMapper modelMapper = new ModelMapper ();
        modelMapper.getConfiguration ()
                .setSkipNullEnabled (true);
        return modelMapper;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory () {
        return new JedisConnectionFactory ();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate () {
        RedisTemplate<String, Object> template = new RedisTemplate<> ();
        template.setConnectionFactory (jedisConnectionFactory ());
        template.setValueSerializer (new GenericToStringSerializer<Object> (Object.class));
        return template;
    }
}
