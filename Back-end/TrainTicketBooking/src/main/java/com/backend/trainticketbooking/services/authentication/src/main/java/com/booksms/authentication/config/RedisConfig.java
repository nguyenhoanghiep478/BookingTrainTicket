package com.booksms.authentication.config;

import com.booksms.authentication.interfaceLayer.DTO.Request.MultipleLoginInfoDTO;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserInformationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setDatabase(0);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<Integer,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Integer,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        log.info("redis connection factory: {}", redisConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(Integer.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        log.info("redis template: {}", template);
        return template;
    }

    @Bean
    public RedisTemplate<String,String> blackList(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(String.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return template;
    }


    @Bean
    public RedisTemplate<String,Integer> sessionStorage(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(String.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return template;
    }

    @Bean
    public RedisTemplate<String,Object> userSession(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new GenericToStringSerializer<>(String.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, UserInformationDTO> userInformationRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserInformationDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Sử dụng StringRedisSerializer cho key
        template.setKeySerializer(new StringRedisSerializer());

        // Cấu hình ObjectMapper
        ObjectMapper objectMapper = createObjectMapper();

        // Tạo Jackson2JsonRedisSerializer với ObjectMapper tùy chỉnh
        Jackson2JsonRedisSerializer<UserInformationDTO> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, UserInformationDTO.class);

        // Gắn serializer cho value
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // Sử dụng StringRedisSerializer cho key của hash
        template.setHashKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<Integer, MultipleLoginInfoDTO> multipleLoginInfo(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Integer, MultipleLoginInfoDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Sử dụng StringRedisSerializer cho key
        template.setKeySerializer(new GenericToStringSerializer<>(Integer.class));

        // Cấu hình ObjectMapper
        ObjectMapper objectMapper = createObjectMapper();

        // Tạo Jackson2JsonRedisSerializer với ObjectMapper tùy chỉnh
        Jackson2JsonRedisSerializer<MultipleLoginInfoDTO> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, MultipleLoginInfoDTO.class);

        // Gắn serializer cho value
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // Sử dụng StringRedisSerializer cho key của hash
        template.setHashKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    // Tạo ObjectMapper cấu hình sẵn
    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Hỗ trợ kiểu dữ liệu ngày giờ
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Định dạng ISO cho ngày giờ
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Bỏ qua thuộc tính không xác định
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // Bỏ qua các trường null
        return objectMapper;
    }


}
